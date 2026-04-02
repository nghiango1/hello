# Cloud with Linux

## Cloud init

> Openstack will have UI for you to provide `user-data` file directly. But underline will still be qemu regardless, this
help your understanding.
>
> This is just taking forever for me to do it manually, but this should be it

Most of Linux distro will provide Cloud readdy image (Likely to be qemu qcow2 img).
But you will struggle to use it directly:

- No User
- No Password
- No IP

`cloud-init` spec will help to setup the VM on first boot by the design

- Ref: <https://arianfm.medium.com/create-virtual-machine-with-cloud-image-and-cloud-init-config-using-qemu-kvm-8080ee6e7f05>
- Ref2: <https://docs.cloud-init.io/en/latest/reference/examples.html#yaml-examples>

cloud-init will be call by the image, this just by disstro support. To use it:

- Provide CDROM `cloud-init.iso` file at boot.
- That all, check it run progression via Serial:
    - VM use `qemu-system-x86_64` to run can do `Ctrl + Alt + 3` to goes over Serial.
    - `virt-manager` (VMM) will have UI for you to look at Serial

Some line like this will be showing
```
[cloud-init] ....
```

### `cloud-init.iso` creation

You can build it using `cloud-localds` utility, for manually handle them.

```sh
sudo dnf install cloud-utils
```

```sh
cloud-localds cloud-init.iso user-data meta-data
```

### `user-data`

Password is for noob, just like me, because I don't know what is the IP so i can SSH into the machine :(

- `passwd` will need hash value of the password, and will be put in shadow file literally
- `lock_passwd` need to set to `false`. Or there will be a `!` value infront of the `passwd` and nullify it

> I stuck at it and can't login into the system for whole morning doing this thing

Easier to graph is:

- `default` the default user of the image, have it and we can keep that account (eg: `ubuntu`).
- `name` the user name
- `ssh_authorized_keys` so we can ssh into the created VM

```yaml
users:
  - default
  - name: osadmin
    lock_passwd: false
    passwd: $y$j9T$RTYZDcbkB8gekMahoLWNJ0$o0sU5FZ5a0EDyjCwB1fKApuq.ulw6kJA6hzHjSxkSL4
    ssh_authorized_keys:
      - ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIDXHwH+Z68UMIXkAEdcetSXh8+nwTTH0ZqUbJFbtTEgK ylongkaka@fedora
    sudo: ['ALL=(ALL) NOPASSWD:ALL']
    groups: sudo
    shell: /bin/bash


#   passwd: The hash -- not the password itself -- of the password you want
#           to use for this user. You can generate a hash via:
#               mkpasswd --method=SHA-512 --rounds=4096
#           (the above command would create from stdin an SHA-512 password hash
#           with 4096 salt rounds)
```

If you setup passwd correctly, you can use serial or VGA session to login into the machine.

- Recheck username if you can't
- Maybe the password have a wrong hash value, or `look_passwd` just not working and the password is still disabled with a `!`
then there no luck for you

Now how to acutally ssh into it, assumming `passwd` did not work. Here I just have a full `user-data` file

- `runcmd` help us to run command(s), force to get IP there is great
- `ssh` now should be possible

```yaml
#cloud-config
hostname: my-vm
fqdn: my-vm.local
manage_etc_hosts: true
users:
  - default
  - name: osadmin
    lock_passwd: false
    passwd: $y$j9T$RTYZDcbkB8gekMahoLWNJ0$o0sU5FZ5a0EDyjCwB1fKApuq.ulw6kJA6hzHjSxkSL4
    ssh_authorized_keys:
      - ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIDXHwH+Z68UMIXkAEdcetSXh8+nwTTH0ZqUbJFbtTEgK ylongkaka@fedora
    sudo: ['ALL=(ALL) NOPASSWD:ALL']
    groups: sudo
    shell: /bin/bash
package_update: true
packages:
  - qemu-guest-agent
  - curl
  - git
runcmd:
  - ip a

```

- Else we can start the VM with NAT and port binding. If you can edit how the VM machine is run, then here is the command.
`net user,hostfwd=tcp::2222-:22` will expose VM 22 port to 2222 port in main devices

```sh
qemu-system-x86_64 \
    -name my-vm \
    -m 2048 \
    -smp 2 \
    -drive file=my-vm.qcow2,format=qcow2 \
    -cdrom cloud-init.iso \
    -net nic,model=virtio \
    -net user,hostfwd=tcp::2222-:22
```

> `ssh -p 2222 osadmin@localhost`, make sure the user VM is correct, and that should be it. Change the password as you like
> if this success. The `file=my-vm.qcow2` will be cover next

### Minor `meta-data`

I don't know, doesn't seem that important

```
instance-id: my-vm
local-hostname: my-vm
```

### Create the image file

Image file that provided by Ubuntu, Fedora. Here is example download link

- <https://fedoraproject.org/cloud/download/> -> [Qcow2](https://ftp.yz.yamagata-u.ac.jp/pub/linux/fedora-projects/fedora/linux/releases/43/Cloud/x86_64/images/Fedora-Cloud-Base-Generic-43-1.6.x86_64.qcow2)
- <https://cloud-images.ubuntu.com/minimal/releases/plucky/release/> -> [QCow2](https://cloud-images.ubuntu.com/minimal/releases/plucky/release/ubuntu-25.04-minimal-cloudimg-amd64.img)

Usually come with a fixed root size. Also by running VM directly using the download image, you will have to download new one right after
every time you want to create a VM.

Here, just use this to create a new image file. The base-image and size also in the command

```sh
qemu-img create -f qcow2 -o backing_file=ubuntu-25.04-minimal-cloudimg-amd64.img,backing_fmt=qcow2 my-vm.qcow2 10G
```
