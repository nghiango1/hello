# yd

The yq is a command line based YAML processor which allows manipulating and extract values from YAML data.

## Containerized yd

This is an Ubuntu Jammy image with only [yq installed](https://lindevs.com/install-yq-on-ubuntu/) for offline used

To create image file, we using these set of command

```sh
sudo docker build -t ubuntu-yq .
sudo docker save ubuntu-yq -o ubuntu-yq
sudo chmod 666 ubuntu-yq
sudo chown osadmin:osadmin ubuntu-yq
#          ^^^^^^^ change this to current user
```
