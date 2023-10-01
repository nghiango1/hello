# Using a base container

```sh
sudo docker pull ubuntu:20.04
```

or we can use latest LST `ubuntu:latest`


# Runing a container

```sh
sudo docker run -it --name my-ubuntu-container ubuntu:20.04
```

This open a shell session, again, it have notthing to begin with and you can't even apt install

# Setup new containner

Create new `dockerfile`, with this content
- Here we have a two build process, one to build a application from a golang docker images
- Other, that copy our build file, from the build only step 1 into our new docker images


```dockerfile
FROM golang:1.21
WORKDIR /src
COPY <<EOF ./main.go
package main

import "fmt"

func main() {
  fmt.Println("hello, world")
}
EOF
RUN go build -o /bin/hello ./main.go

FROM scratch
COPY --from=0 /bin/hello /bin/hello
CMD ["/bin/hello"]
```

This need to be build result into a single hello images.

```sh
sudo docker build -t hello .
```

Which then can be run like any other images.

```sh
sudo docker images
sudo docker run hello
sudo docker run <imageid>
```

# Inspect image

Here I testing inspect our `hello` images just build

```sh
sudo docker image inspect hello
```

Output:


```json
[
{
    "Id": "sha256:b481b871ee6b28346399e0bf733f40af6a862b9d052b06b2971330bdc51a3285",
        "RepoTags": [
            "hello:latest"
        ],
        "RepoDigests": [],
        "Parent": "",
        "Comment": "buildkit.dockerfile.v0",
        "Created": "2023-09-06T16:24:55.967268977+07:00",
        "Container": "",
        "ContainerConfig": {
            "Hostname": "",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": null,
            "Cmd": null,
            "Image": "",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": null
        },
        "DockerVersion": "",
        "Author": "",
        "Config": {
            "Hostname": "",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
            ],
            "Cmd": [
                "/bin/hello"
            ],
            "ArgsEscaped": true,
            "Image": "",
            "Volumes": null,
            "WorkingDir": "/",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": null
        },
        "Architecture": "amd64",
        "Os": "linux",
        "Size": 1802287,
        "VirtualSize": 1802287,
        "GraphDriver": {
            "Data": {
                "MergedDir": "/var/lib/docker/overlay2/f8917gwiovktrmmcbob6gp9yd/merged",
                "UpperDir": "/var/lib/docker/overlay2/f8917gwiovktrmmcbob6gp9yd/diff",
                "WorkDir": "/var/lib/docker/overlay2/f8917gwiovktrmmcbob6gp9yd/work"
            },
            "Name": "overlay2"
        },
        "RootFS": {
            "Type": "layers",
            "Layers": [
                "sha256:2257c99c5740e38dc1139e626048254ff6ecbfca5c5471814d15e9d68ba11628"
            ]
        },
        "Metadata": {
            "LastTagTime": "2023-09-06T16:24:56.03570449+07:00"
        }
}
]
```

