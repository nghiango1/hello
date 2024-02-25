Cài đặt Openshift 4 trong môi trường không có kết nối internet trên VMware vSphere

# I. Chuẩn bị

## Yêu cầu cơ sở hạ tầng VMware vSphere

VMware vSphere version 7.0 Update 2 or later

| Virtual environment product   | Required version    |
|--------------- | --------------- |
| VMware virtual hardware   | 15 or later   |
| vSphere ESXi hosts   | 7.0 Update 2 or later   |
| vCenter host   | 7.0 Update 2 or later   |

Trỏ dns cho api vips và ingress và đảm bảo kết nối từ node tới dns:
- api Vip:    api.openshift.devtest.bca
- ingress: *.apps.openshift.devtest.bca
- quay:  quay.openshift.dev (máy bastion có internet)
- quay:  quay.openshift.devtest.bca (nằm ở máy không có internet)

## 1. Tạo mirror registry.

Truy cập vào https://console.redhat.com để tải về
- pull secret
- oc command-line tool (tải đúng ver  của openshift 4.13.2 [tại đây](https://access.redhat.com/downloads/content/290/ver=4.13/rhel---8/4.13.2/x86_64/product-software))
- openshift-install tool (tải đúng ver  của openshift 4.13.2 [tại đây](https://access.redhat.com/downloads/content/290/ver=4.13/rhel---8/4.13.2/x86_64/product-software))
- mirror-registry

Copy và giải nén các file download về bastion host
```sh
wget https://mirror.openshift.com/pub/openshift-v4/x86_64/clients/ocp/4.13.2/openshift-install-linux-4.13.2.tar.gz [user@bastion ~]# wget https://mirror.openshift.com/pub/openshift-v4/x86_64/clients/ocp/4.13.2/openshift-client-linux-4.13.2.tar.gz
tar zxf openshift-client-linux.tar.gz
tar zxf openshift-install-linux.tar.gz
mv oc kubectl openshift-install /usr/bin
```

Tạo thư mục ~/mirror, cho pull-secret.txt vào thư mục mirror
```sh
mkdir ~/mirror && mv pull-secret.txt mirror/
```

Cài đặt các gói phần mềm cần thiết trên bastion host
```sh
cd mirror
sudo yum install -y podman httpd-tools openssl tmux net-tools nmstate git golang make zip bind-utils jq
```

Thiết lập thư mục chứa nội dung cho mirror registry và tiến hành cài đặt registry
```sh
hostnamectl set-hostname quay.openshift.dev
export REGISTRY_SERVER=quay.openshift.dev 
sudo mkdir /quay
tar xzvf mirror-registry.tar.gz 
sudo ./mirror-registry install –quayHostname $REGISTRY_SERVER --quayRoot /quay
```

Cài đặt xong, update trusted CA trên bastion host
```
sudo cp /quay/quay-rootCA/rootCA* /etc/pki/ca-trust/source/anchors/ -v
sudo update-ca-trust extract
```

Login vào registry
```sh
podman login --authfile pull-secret.txt -u init -p $REGISTRY_PW quay.openshift.dev:8443 --tls-verify=false
```

Sau câu lệnh đăng nhập này, một mục nội dung secret tương ứng với tên của mirror registry sẽ đựoc tự động thêm vào pull-secret.txt, chúng ta có thể kiểm tra bằng cách xem nội dung file này. Tiếp theo, sử dụng jq để chuyển pull-secret.txt sang dạng json và copy vào các đường dẫn cần thiết
```sh
cat ./pull-secret.txt | jq . > pull-secret.json
mkdir -p ~/.config/containers
cp pull-secret.json ~/.config/containers/auth.json
mkdir ~/.docker
cp pull-secret.json ~/.docker/config.json
```

Đặt các biến môi trường cần thiết
```sh
export OCP_RELEASE=4.13.2
export LOCAL_REGISTRY=’quay.openshift.devtest.bca:8443’
export LOCAL_REPOSITORY=’ocp4/openshift4’
export PRODUCT_REPO='openshift-release-dev'
export LOCAL_SECRET_JSON=’~/Documents/pull-secret.json’
export RELEASE_NAME="ocp-release"
export ARCHITECTURE=x86_64
export REMOVABLE_MEDIA_PATH=’/var/mirror_data/’
```

### Mirror image về thư mục mirror_data không dùng plugin:

```sh
oc adm release mirror -a ${LOCAL_SECRET_JSON} --to-dir=${REMOVABLE_MEDIA_PATH}/mirror quay.io/${PRODUCT_REPO}/${RELEASE_NAME}:${OCP_RELEASE}-${ARCHITECTURE}
```

Tải image đã mirror lên quay.openshift.dev:8443 (nếu gặp lỗi thì thêm tùy chọn --skip-missing, --continue-error). Sau khi chạy thành công sẽ xuất hiện thông tin imageContentSources. Lưu thông tin này để dùng khi tạo install-config.yaml

Nén thư mục mirror dạng tar và copy sang vùng không có internet, sau đó upload lên quay registry vùng không có internet.
```sh
tar -czvf mirror_data.tar.gz mirror/
oc image mirror -a ${LOCAL_SECRET_JSON} --from-dir=${REMOVABLE_MEDIA_PATH}/mirror "file://openshift/release:${OCP_RELEASE}*" ${LOCAL_REGISTRY}/${LOCAL_REPOSITORY}
```

> Lưu ý: REMOVABLE_MEDIA_PATH phải giống nhau ở cả 2 repo và là đường dẫn tuyệt đối, nếu muốn copy qua server khác thì phải nén ở dạng tar trước khi copy 

### Mirror image Dùng plugin oc-mirror để tạo data mirror.

#### Init cấu hình:

```sh
oc-mirror init --registry $REGISTRY_SERVER:8443/ocp4/openshift4/mirror/oc-mirror-metadata > imageset-config.yaml
```

Có thể chỉnh sửa file config để thêm operator mong muốn.

Để xem danh sách các operator và channel có thể sử dụng với phiên bản openshift 4.13 chạy lệnh:
```sh
oc-mirror list operators --catalog registry.redhat.io/redhat/redhat-operator-index:v4.13
```

Cuối cùng, chạy lệnh sau để thực hiện mirror

```sh
oc-mirror --config=./imageset-config.yaml file://<path_out_dir> --continue-on-error
```

> Lưu ý:
> - quá trình này sẽ tạo 1 thư mục tên là oc-mirror-workspace về sau sẽ dùng nội dung trong thư mục này để hiển thi danh sách các operator được add vào trong file imageset-config.yaml
> - quá trình mirror sẽ kéo dài tùy theo số lượng operator và chất lượng đường truyền internet
> - nếu gặp lỗi trong quá trình mirror, có thể thêm khóa --continue-on-error , --skip-missiong vào câu lệnh oc-mirror ở trên để hoàn thành quá trình mirror
> - sau đó để upload lên repo không có internet để sử dụng:
oc-mirror  --from=./mirror_seq1_000000.tar \ docker://quay.openshift.devtest.bca:8443

## 2. Tạo một cặp khóa để truy cập SSH vào các node cluster

```sh
ssh-keygen -t ed25519 -N '' -f node_ssh_key
```

# Method 1: Cài qua Agent base (build ISO file)

> Yêu cầu: Trỏ thêm DNS cho các node master và worker.

Tạo thư mục cluster và copy file install-config.yaml , agent-config.yaml vào. Sau đó tắt kết nối internet của bastion và chạy lệnh tạo ISO:
```sh
openshift-install --dir cluster –log-level=debug agent create image
```

Tiếp theo, thực hiện các thao tác:
- Upload file agent.x86_64.iso lên vmware,
- mount nó vào các node đã được tạo sẵn dưới dạng CD-ROM,
- và Power On các VM lên. Việc cài đặt openshift sẽ đựoc tự động thực hiện trong quá trình các node boot.
- Nên boot từng máy một, chờ master01 xong mới tiếp tục boot các máy còn lại

Để theo dõi quá trình cài đặt, thực hiện lệnh sau:
```sh
openshift-install agent wait-for install-complete --dir cluster
```

> Lưu ý: file agent.x86_64.iso chỉ được dùng để cài đặt trong 24h. quá trình này sẽ kéo dài khoảng 60', trong quá trình đó, các node sẽ đựoc tự động reboot vài lần, và có thể sẽ thấy nột số thông báo FAILED, sau khi có thông báo việc triển khai thành công hay thất bại. Nếu thất bại, cần:
> - xóa hết nội dung trong thư mục cluster,
> - xem lại nội dung các file yaml xem đã đúng chưa (IP, MAC, Gateway, DNS, sshKey, secret,...)
> - copy các file install-config.yaml , agent-config.yaml vào lại thư mục này,
> - ... và thực hiện lại câu lệnh openshift-install ở trên để tạo lại file iso, mount CD-ROM và reboot các máy

> Lưu ý:  Config imageContentSources trong file install-config.yaml
> - Nếu sử dụng plugin oc-mirror để pull image thì mở file imageContentSourcePolicy.yaml trong thư mục results (ví dụ oc-mirror-workspace/results-1639608409/) và copy nội dung repositoryDigestMirrors trong file để sửa vào file install-config.yaml.
> - Nếu mirror qua lệnh oc adm release thì copy nội dung imageContentSources tương ứng sau khi chạy lệnh để sửa vào file install-config.yaml
> - additionalTrustBundle: giá trị là nội dung file etc/pki/ca-trust/source/anchors/rootCA.pem
> - sshKey: nội dung file node_ssh_key.pub


Tiếp theo, disable default OperatorHub và install ImageContentSourcePolicy và CatalogSource resources vào cluster:
```sh
oc patch OperatorHub cluster --type json -p '[{"op": "add", "path": "/spec/disableAllDefaultSources", "value": true}]'
oc apply -f ./oc-mirror-workspace/results-1639608409/
oc get imagecontentsourcepolicy --all-namespaces
oc get catalogsource --all-namespaces
```

## Add thêm worker cho cluster sử dụng ova

Lấy base64 của worker node bằng lệnh:

```sh
oc extract -n openshift-machine-api secret/worker-user-data-managed --keys=userData --to=- | base64 -w0
```
> Download file ova [tại đây](https://mirror.openshift.com/pub/openshift-v4/dependencies/rhcos/latest/rhcos-vmware.x86_64.ova)

Cấu hình DNS cho node mới (vd: worker03.openshift.devtest.bca – 192.168.101.113)
1. Import OVA vào vSphere cho vào thư mục Templates trên vcenter
2. Clone VM
    - Chọn customize hardware
    - chỉnh sửa tham số specs cần thiết
    - vào tab VM Options, mục Advanced
        - chọn Latency Sensitivity = Normal
        - Click Edit Configuration, and on the Configuration Parameters window, click Add Configuration Params. Gán param:
            - guestinfo.ignition.config.data: paste nội dung base64 phía trên.
            - guestinfo.ignition.config.data.encoding: để giá trị là base64.
            - disk.EnableUUID: để giá trị là TRUE
            - guestinfo.afterburn.initrd.network-kargs: ip=<ip>::<gateway>:<netmask>:<hostname>:<iface>:none nameserver=srv1 [nameserver=srv2 [nameserver=srv3 [...]]]

            vd: ip=192.168.101.113::192.168.101.254:255.255.255.0:worker03.openshift.devtest.bca:ens192:none:172.119.3.1

            - stealclock.enable:   TRUE

3. Power On VM, chờ quá trình cài đặt:
    Chạy lệnh :

        oc get -w csr

4. nếu thấy có request pending thì cần approve chạy tiếp:

        oc adm certificate approve <request_name>

5. Để Kiểm tra node mới đã được add thành công chưa chạy lệnh: 

        oc get no

## Add thêm worker cho cluster sử dụng iso

1. Lấy file worker.ign:

        oc extract -n openshift-machine-api secret/worker-user-data-managed --keys=userData --to=- > worker.ign

        sha512sum worker.ign (Lưu lại sha512, có thể  dùng tới ở lệnh coreos-instlaller)

2. Copy file vào /var/www/html

        cp worker.ign /var/www/html

3. Kiểm tra xem file worker.ign đã truy cập được chưa: 

        curl -k http://www.quay.openshift.devtest.bca/worker.ign

4. Lấy link file iso để boot vm:

        RHCOS_VHD_ORIGIN_URL=$(oc -n openshift-machine-config-operator get configmap/coreos-bootimages -o jsonpath='{.data.stream}' | jq -r '.architectures.x86_64.artifacts.metal.formats.iso.disk.location')

        echo $RHCOS_VHD_ORIGIN_URL

5. Download iso file và tải file lên vCenter Content Library hoặc Datastore

6. Khai báo tên node lên DNS (giống như khai báo các worker node trong quá trình cài cluster)

7. mount CDROM trỏ đến file iso RHCOS đã được download ở bước trước

8. boot vm lên từ file ISO

9. cấu hình network cho VM (có thể sử dụng giao diện nmtui), sau khi cấu hình xong thử xem có lấy được file worker ignition từ web server hay không

        curl -k http://quay.openshift.devtest.bca/worker.ign

10. Chạy câu lệnh coreos-installer để cài

        sudo coreos-installer install --copy-network --insecure --insecure-ignition --ignition-url=http://quay.openshift.devtest.bca/worker.ign /dev/sda

    Đợi cho RHCOS được cài đặt thành công, sau đó reboot máy và chờ thao tác cài đặt worker node (khoảng 10')

11. Đồng thời trên 1 màn hình terminal có kết nối với cluster, chạy câu lệnh

        oc get -w csr

    mỗi khi có 1 csr pending chúng ta sẽ phải approve nó bằng lệnh

        oc adm certificate approve <csr_name>

    Sẽ có 2 csr với mỗi node. Có thể approve trên trang console của cluster


# Method 2: Cài qua IPI

> Yêu cầu: Sử dụng DHCP cho mạng và đảm bảo rằng máy chủ DHCP được cấu hình để cung cấp các địa chỉ IP liên tục cho các máy cụm. Tất cả các nút phải ở cùng một Vlan.

1. Adding vCenter root CA certificates to your system trust
    Từ trang chủ vCenter, tải xuống chứng chỉ CA gốc của vCenter. Nhấp vào Tải xuống Chứng chỉ Root CA đáng tin cậy trong phần SDK Dịch vụ web VSphere. Tải xuống tệp <vCenter>/certs/download.zip sau đó giải nén.
    Tiếp tục copy cert vào hệ thống để trust

        cp certs/lin/* /etc/pki/ca-trust/source/anchors
        update-ca-trust extract
  
    Dowdload template ova rhcos tại đây rồi copy vào /var/www/html

5. Tạo file install-config.yaml
    - Init file install-config: 

            openshift-install create install-config --dir <installation_dir>

    Sau khi điền các thông tin cơ bản, tiếp tục mở file và chỉnh sửa.

    - Khai báo url clusterOSImage (http://quay.openshift.devtest.bca/rhcos-vmware.x86_64.ova)
    - Khai báo pullSecret: lấy trong pull-secret.json
    - additionalTrustBundle: giá trị là nội dung file etc/pki/ca-trust/source/anchors/rootCA.pem
    - sshKey: nội dung file node_ssh_key.pub
    - imageContentSources: thay bằng nội dung mirror

    Sau khi hoàn tất chỉnh sửa -> lưu backup lại file.

    > Lưu ý:  Config imageContentSources trong file install-config.yaml
    > - Nếu sử dụng plugin oc-mirror để pull image thì mở file imageContentSourcePolicy.yaml trong thư mục results (ví dụ oc-mirror-workspace/results-1682697932/) và copy nội dung repositoryDigestMirrors trong file để sửa vào file install-config.yaml.
    > - Nếu mirror qua lệnh oc adm release thì copy nội dung imageContentSources tương ứng sau khi chạy lệnh để sửa vào file install-config.yaml
    > - additionalTrustBundle: giá trị là nội dung file etc/pki/ca-trust/source/anchors/rootCA.pem
    > - sshKey: nội dung file node_ssh_key.pub

6. Deploy cluster

    Copy file install-config.yaml vào thư mục cluster và chạy lệnh

        openshift-install create cluster --dir cluster --log-level=debug
	
    Trong bất cứ trường hợp lỗi hay chạy lại đều phải xóa sạch thư mục cài đặt, kiểm tra lại thông tin cấu hình sau đó copy lại file install-config.yaml rồi chạy lại lệnh cài đặt.

