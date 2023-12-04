# ZFS storage

I currently using ZFS storage, (Ubuntu helper that use encripted storage LUKS, which come with mounted ZFS pool split)
So podman throwing this to me

```sh
→ podman run hello-world
Error: 'overlay' is not supported over zfs, a mount_program is required: backing file system is unsupported for this graph driver
```

Which mean, I need to learn some ZFS, so following /hello/container/zfs.

```sh
→ sudo zfs create rpool/mynewdataset
→ zfs list
NAME                                               USED  AVAIL     REFER  MOUNTPOINT
bpool                                              297M  1.46G       96K  /boot
bpool/BOOT                                         297M  1.46G       96K  none
bpool/BOOT/ubuntu_of0te7                           296M  1.46G      296M  /boot
rpool                                             24.2G   201G      192K  /
rpool/ROOT                                        14.1G   201G      192K  none
rpool/ROOT/ubuntu_of0te7                          14.1G   201G     5.69G  /
rpool/ROOT/ubuntu_of0te7/srv                       192K   201G      192K  /srv
rpool/ROOT/ubuntu_of0te7/usr                      1.48M   201G      192K  /usr
rpool/ROOT/ubuntu_of0te7/usr/local                1.30M   201G     1.30M  /usr/local
rpool/ROOT/ubuntu_of0te7/var                      8.42G   201G      192K  /var
rpool/ROOT/ubuntu_of0te7/var/games                 192K   201G      192K  /var/games
rpool/ROOT/ubuntu_of0te7/var/lib                  8.38G   201G     8.21G  /var/lib
rpool/ROOT/ubuntu_of0te7/var/lib/AccountsService   212K   201G      212K  /var/lib/AccountsService
rpool/ROOT/ubuntu_of0te7/var/lib/NetworkManager    464K   201G      464K  /var/lib/NetworkManager
rpool/ROOT/ubuntu_of0te7/var/lib/apt               104M   201G      104M  /var/lib/apt
rpool/ROOT/ubuntu_of0te7/var/lib/dpkg             63.4M   201G     63.4M  /var/lib/dpkg
rpool/ROOT/ubuntu_of0te7/var/log                  34.8M   201G     34.8M  /var/log
rpool/ROOT/ubuntu_of0te7/var/mail                  192K   201G      192K  /var/mail
rpool/ROOT/ubuntu_of0te7/var/snap                 2.69M   201G     2.69M  /var/snap
rpool/ROOT/ubuntu_of0te7/var/spool                 932K   201G      932K  /var/spool
rpool/ROOT/ubuntu_of0te7/var/www                   192K   201G      192K  /var/www
rpool/USERDATA                                    9.55G   201G      192K  /
rpool/USERDATA/root_xf1jjm                        1.23M   201G     1.23M  /root
rpool/USERDATA/ylong_xf1jjm                       9.55G   201G     9.55G  /home/ylong
rpool/keystore                                     518M   201G     63.4M  -
rpool/mynewdataset                                 192K   201G      192K  /mynewdataset
```

This mainly because I runging rootless.

So a even better solution is using a rootful `podman`, or just use `docker` instead
