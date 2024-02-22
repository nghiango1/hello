# Oracle RAC

High avaiability and Redundancy for Oracle database server

## Notes

> Hand-on Lab for install Oracle RAC on VMware VM with production like redudancy level

### Prerequired

#### Using VMware and created needed LAP environment

Need at least  2 VM
- Hardware Requirements: 4vCore 16GB RAM 256 GB Storage + 100 GB x2 Share RAC Storage
- Use Oracle DB (Oracle RAC) install and Oracle Linux that comparable base on your preference
    - Here is reference which being used: Oracle DB 19c with Oracle Linux R7.U9
    - Download and upload them into VMware datastore
- Completely create two VM "normally" (or minimally) till it up and running
    - Use only 1 each with the same configuration
    - We will add more Hard disk, SCSI, Network later in order so that we can get a predictable interface for all VM
    - Yum upgrade for latest packages and setup NTP server (*)
- Prefered use external Network (first Distributed vSwtich).
    - So you can `ssh` into VM for better remote experience
    - If not, use VMware Web/Tools console UI
- (multipath) Adding `disk.EnableUUID` with value `TRUE` to Advantage parameter in VMware VM setting

After that, start config
- Setup two share disk with Thick + Eager zerod provision with multi-writer sharing mode
    - Setup two new disk in OracleRAC-01
    - Adding them into OracleRAC-02
- Add the seconed Distributed vSwtich
    - As you already have External network on the first step, We now add the private network for internal connection beetween two Oracle RAC server

#### OS config

OS Installation process, which is mostly default
- Reference for partition OS storage (256GB)
    ```
    # /dev/sda
    <partition_table> 1K
    /var    16G
    /u01    168G
    /home   12G
    /swap   16G
    /       32G
    /tmp    12G
    ```
- Config to enable networks, hostsname. Reference value
    ```
    ens192 => on
    ens224 => on
    hostnames => rac-19c-01, rac-19c-02
    ```

> (*): For even restricted environment, this notes lab have full offline install

Adding ISO as local repo
- mount cdrom
    ```sh
    mkdir /mnt/cd-room
    mount /dev/cdrom /mnt/cdrom
    ```
- Disable all other (external) repo
    ```sh
    yum-config-manager --disable \*
    ```
- Add new record (repo) to `/etc/yum.repos.d/`, create a new config file with this content
    ```new-yum.repo
    [OL79]
    name=Oracle Linux 7.9 x86_64
    baseurl=file:///mnt/cdrom
    gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY
    gpgcheck=1
    enabled=1
    ```
- Check with `yum repolist`
    ```sh
    yum repolist
    # # Sample output
    # Loaded plugins: ulninfo
    # OL79  | 3.6 kB  00:00:00
    # (1/2): OL79/group_gz   | 131 kB  00:00:00
    # (2/2): OL79/primary_db  | 5.2 MB  00:00:00
    # repo id           repo    name    status
    # OL79 Oracle Linux 7.9     x86_64  5,210
    # repolist: 5,210
    ```

Setup local NTP server
- We use `chrony` as our ntp client, I not setup NTP server yet so just use default server
    ```sh
    yum -y install chrony
    ```
- Add your local NTP server in chrony config file `/etc/chrony.conf`
    ```
    # Add local NTP server
    server 10.1.1.254 iburst
    ```
- Enable the service after install
    ```sh
    systemctl enable chrony
    systemctl start chrony
    ```
- Check status to see if it active 
    ```sh
    systemctl status chrony
    ```
- Recheck installed local server with `chronyc sources`

Disable firewall - default is `firewalld` service
```sh
systemctl disable firewalld
systemctl stop firewalld
```

Set up these two diferent network interface
- We can use `nwtui`, activate and deactivate after make change
- If you follow minimal setup on VMware, this require you add another Network Adapter to VM
- In my VMware configuration (which already setup DHCP server for both network):
    - ens192 -> 192.168.2.0/22: For external/internet connection
    - ens224 -> 10.1.1.0/24: For privated/internal

Setup shared disk
- If you follow minimal setup on VMware, this require you add two new Hard disk to rac-19c-01
    - Thick provision + Multi writer + dependant. Adding `disk.EnableUUID` with value `TRUE` to Advantage parameter in VMware VM setting
    - After it finish created, add Existed Hard disk to rac-19c-02
    - We can setup SCSI to make two different route to the same VMware hard disk (simulating redundancy network path for storage access in LAB environment)
        - Create new SCSI controler for rac-19c-01
        - Add Existed Hard disk to rac-19c-01
            - Re-add created shared disk
            - Config it with multiwriter
        - Diffrence comparation, rac-19c-01 have 04 100G disk
        ```
        [root@rac-19c-01 bin]# lsblk -a
        NAME        MAJ:MIN RM   SIZE RO TYPE MOUNTPOINT
        sdd           8:48   0   100G  0 disk
        sdb           8:16   0   100G  0 disk
        sr0          11:0    1   4.5G  0 rom
        sde           8:64   0   100G  0 disk
        sdc           8:32   0   100G  0 disk
        sda           8:0    0   256G  0 disk
        ├─sda2        8:2    0   255G  0 part
        │ ├─ol-swap 252:1    0   7.9G  0 lvm  [SWAP]
        │ ├─ol-home 252:2    0 197.1G  0 lvm  /home
        │ └─ol-root 252:0    0    50G  0 lvm  /
        └─sda1        8:1    0     1G  0 part /boot
        ```
        vs rac-19c-02 have 02 100G disk
        ```
        [root@rac-19c-02 osadmin]# lsblk -a
        NAME        MAJ:MIN RM   SIZE RO TYPE MOUNTPOINT
        sdb           8:16   0   100G  0 disk
        sr0          11:0    1   4.5G  0 rom
        sdc           8:32   0   100G  0 disk
        sda           8:0    0   256G  0 disk
        ├─sda2        8:2    0   255G  0 part
        │ ├─ol-swap 252:1    0   7.9G  0 lvm  [SWAP]
        │ ├─ol-home 252:2    0 197.1G  0 lvm  /home
        │ └─ol-root 252:0    0    50G  0 lvm  /
        └─sda1        8:1    0     1G  0 part /boot
        ```

- Format and partition new added disk using `fdisk`, or tui version `cfdisk`
    ```
    fdisk /dev/sdb
    # m for help
    # n for create new partition
    # w for save and exit
    fdisk /dev/sdc
    ```
    - We then asked to answer config options, which mostly will be default value
    - For [p]rimary/[e]xtended: Primary mean it bootable, which we don't need for our shared disk. But we will use [p]rimary here
    - Reboot will be needed (?), recheck partition with `lsblk`

- Mount directory for shared disk
    - Normally, with redundancy, we can see 04 difference disk with lsblk. But we accually have only 02 disk.
        - We can see that:
            - /dev/sdb is the same with /dev/sdd
            - /dev/sdc is the same with /dev/sde
            - If we format one disk, the other will be update as well (example `fdisk` create partition).
            > You may need to reboot the VM
    - This need to be handle with `multipath` setup

- Set-up `multipath`
    - This needed when we have redundancy network for storage access, which can be simulate using multi SCSI confiuration in VMware 
    - Install `multipath` and enable multipathd service via systemctl
        ```sh
        yum -y install device-mapper-multipath
        ```
    - Generate config file
        ```sh
        mpathconf
        # # Output:
        # multipath is enabled
        # find_multipaths is enabled
        # user_friendly_names is enabled
        # dm_multipath module is not loaded
        # multipathd is not running
        mpathconf --enable
        ```
    - Get disk ID_SERIAL, via `udevadm` command
        ```sh
        udevadm info /dev/sdb
        ```
        > If you some how not find ID_SERIAL, it mean that VMware option for disk.EnableUUID isn't set.

    - Adding multipath config `/etc/multipath.conf` file
        ```
        multipaths {
            multipath {
                wwid    <ID_SERIAL>
                alias   <orcdisk01-name>
            }
        }
        ```
        here is a reference config, wwid is difference so it not cop-able
        ```
        multipaths {
                # multipath {
                #         wwid                    36000c29e3747de97803f9cccd9ac0262
                #         alias                   odisk01
                # }
                multipath {
                        wwid                    36000c29eceeba3b0336f7d8a6dfd8ad2
                        alias                   odisk02
                }
                multipath {
                        wwid                    36000c29872ac4289cdc922f9ba77dabe
                        alias                   odisk03
                }
        }
        ```
    - Start and enable `multipathd` services
        ```
        systemctl enable multipathd
        systemctl start multipathd
        ```
    - Reference `multipath -ll` check after finished setup multipaths
        ```
        multipath -ll
        # # Sample output
        # odisk03 (36000c29872ac4289cdc922f9ba77dabe) dm-3 VMware  ,Virtual disk
        # size=100G features='0' hwhandler='0' wp=rw
        # |-+- policy='service-time 0' prio=1 status=active
        # | `- 1:0:1:0 sde 8:64 active ready running
        # `-+- policy='service-time 0' prio=1 status=enabled
        #   `- 0:0:2:0 sdc 8:32 active ready running
        # odisk02 (36000c29eceeba3b0336f7d8a6dfd8ad2) dm-2 VMware  ,Virtual disk
        # size=100G features='0' hwhandler='0' wp=rw
        # |-+- policy='service-time 0' prio=1 status=active
        # | `- 0:0:1:0 sdb 8:16 active ready running
        # `-+- policy='service-time 0' prio=1 status=enabled
        ```
    - Reference `lsblk -a` check after finish setup multipaths
        ```
        [root@rac-19c-01 yum.repos.d]# lsblk -a
        NAME          MAJ:MIN RM   SIZE RO TYPE  MOUNTPOINT
        sdd             8:48   0   100G  0 disk
        └─odisk02     252:2    0   100G  0 mpath
          └─odisk02p1 252:6    0   100G  0 part
        sdb             8:16   0   100G  0 disk
        └─odisk02     252:2    0   100G  0 mpath
          └─odisk02p1 252:6    0   100G  0 part
        sr0            11:0    1   4.5G  0 rom   /mnt/cdrom
        sde             8:64   0   100G  0 disk
        └─odisk03     252:4    0   100G  0 mpath
          └─odisk03p1 252:5    0   100G  0 part
        sdc             8:32   0   100G  0 disk
        └─odisk03     252:4    0   100G  0 mpath
          └─odisk03p1 252:5    0   100G  0 part
        sda             8:0    0   256G  0 disk
        ├─sda2          8:2    0   255G  0 part
        │ ├─ol-swap   252:1    0   7.9G  0 lvm   [SWAP]
        │ ├─ol-root   252:0    0    50G  0 lvm   /
        │ └─ol-home   252:3    0 197.1G  0 lvm   /home
        └─sda1          8:1    0     1G  0 part  /boot
        ```
    - Mount `multipath` device

- We can create LVM right after
    ```sh
    pvcreate /dev/odisk02p1
    pvcreate /dev/odisk03p1
    vgcreate odisk02 /dev/odisk02p1
    vgcreate odisk03 /dev/odisk03p1
    ```

> Share disk can be config on one VM, other some time will need to reboot to update latest change
> Reinstall OS will only need to

#### RAC config

##### Oracle grid installation
Seting up requirement packages
```sh
yum install -y oracle-database-preinstall-19c
```

Enableing NSCD
```sh
yum install nscd
systemctl enable nscd
systemctl start nscd
```

Disk I/O Scheduler on Linux
- Open/Create new config file
    ```sh
    vi /etc/udev/rules.d/60-oracle-schedulers.rules
    ```
- Add this rule
    ```udev.rules
    ACTION=="add|change", KERNEL=="odisk0[1-3]", ATTR{queue/rotational}=="0", ATTR{queue/scheduler}="deadline"
    ```
- Resart UDEV service
    ```sh
    udevadm control --reload-rules
    ```
- Recheck schduler of ASM_DISK
    ```sh
    cat /sys/block/${ASM_DISK}/queue/scheduler
    # #Example output:
    # noop [deadline] cfq
    ```

Ensure NTP with Oracle Cluster Time Synchronization
- [Ref-link](https://docs.oracle.com/en/database/oracle/oracle-database/19/cwlin/setting-network-time-protocol-for-cluster-time-synchronization.html#GUID-D2CDFAC3-4CE2-4E56-B34F-81D0FD20CEFA)
    > Only needed if we not have NTP server and can't access external NTP server

Create `grid` user that install grid
```sh
useradd grid -g oinstall
passwd grid
```
- Config Resource Limits for grid user (?)
    ```sh
    vi /etc/security/limits.d/oracle-database-preinstall-19c.conf
    ```
    config file example
    ```limits.d.conf
    # oracle-database-preinstall-19c setting for nofile soft limit is 1024
    oracle soft nofile 1024
    grid soft nofile 1024

    # oracle-database-preinstall-19c setting for nofile hard limit is 65536
    oracle hard nofile 65536
    grid hard nofile 65536

    # oracle-database-preinstall-19c setting for nproc soft limit is 16384
    # refer orabug15971421 for more info.
    oracle soft nproc 16384
    grid soft nproc 16384

    # oracle-database-preinstall-19c setting for nproc hard limit is 16384
    oracle hard nproc 16384
    grid hard nproc 16384

    # oracle-database-preinstall-19c setting for stack soft limit is 10240KB
    oracle soft stack 10240
    grid soft stack 10240

    # oracle-database-preinstall-19c setting for stack hard limit is 32768KB
    oracle hard stack 32768
    grid hard stack 32768

    # oracle-database-preinstall-19c setting for memlock hard limit is maximum of 128GB on x86_64 or 3GB on x86 OR 90 % of RAM
    oracle hard memlock 134217728
    grid hard memlock 134217728

    # oracle-database-preinstall-19c setting for memlock soft limit is maximum of 128GB on x86_64 or 3GB on x86 OR 90% of RAM
    oracle soft memlock 134217728
    grid soft memlock 134217728

    # oracle-database-preinstall-19c setting for data soft limit is 'unlimited'
    oracle soft data unlimited
    grid soft data unlimited

    # oracle-database-preinstall-19c setting for data hard limit is 'unlimited'
    oracle hard data unlimited
    grid hard data unlimited
    ```
- Prevent output on stdout or stderr
```
if [ -t 0 ]; then
   stty intr ^C
fi
```

Settin up enviroment variable
- We update `.bash_profile` file
- Setup ORACLE related environment
    > 7 . If the ORACLE_SID, ORACLE_HOME, or ORACLE_BASE environment variables are set in the file, then remove these lines from the file
    > 11. Unset any Oracle environment variables.
    If you have an existing Oracle software installation, and you are using the same user to install this installation, then unset the $ORACLE_HOME, $ORA_NLS10, and $TNS_ADMIN environment variables.
    If you have set $ORA_CRS_HOME as an environment variable, then unset it before starting an installation or upgrade. Do not use $ORA_CRS_HOME as a user environment variable, except as directed by Oracle Support.

    ```sh
    export PATH
    export ORACLE_SID=+ASM2
    export ORACLE_HOME=/u01/app/19.0.0/grid
    ```
- Setup `umask` to 022, so file installation have 644 permission
    ```sh
    umask 022
    ```
- Set `ssh` config to not forward X11 in file `~/.ssh/config`
    ```
    Host *
        ForwardX11 no
    ```
- Set no STDOUT and STDERR
    ```sh
    # Suppress STDOUT anad STDERR
    if [ -t 0 ]; then
       stty intr ^C
    fi
    ```

More ..., finall `.bash_profile` look like this
```sh
# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
        . ~/.bashrc
fi

# Suppress STDOUT anad STDERR
if [ -t 0 ]; then
   stty intr ^C
fi

# User specific environment and startup programs
umask 022

PATH=$PATH:$HOME/.local/bin:$HOME/bin

export PATH
export ORACLE_BASE=/u01/app/grid
export ORACLE_HOME=/u01/app/19.0.0/grid

ASM()
{
export ORACLE_SID=+ASM
echo "ORACLE_SID="$ORACLE_SID
echo "ORACLE_BASE="$ORACLE_BASE
echo "ORACLE_HOME="$ORACLE_HOME
}
```

Pre-made all installation drirectory
```sh
mkdir -p /u01/app/19.0.0/grid
mkdir -p /u01/app/grid
mkdir -p /u01/app/oracle
chown -R grid:oinstall /u01
chown oracle:oinstall /u01/app/oracle
chmod -R 775 /u01/
```

Extract installer
```sh
cd /u01/app/19.0.0/grid/
unzip -q ~/LINUX.X64_193000_grid_home.zip
```

GUI - installation
- Setup network interface
    - ens192: External/ plublic
    - ens224: ASM and private
- 
