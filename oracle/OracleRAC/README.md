# Oracle RAC

High avaiability and Redundancy for Oracle database server

## Notes

> Hand-on Lab for install Oracle RAC on VMware VM with production like redudancy level

### Prerequired

#### Using VMware and created needed LAP environment

Need at least  2 VM
- Use Oracle DB (Oracle RAC) install and Oracle Linux that comparable base on your preference
    - Here is reference which being used: Oracle DB 19c with Oracle Linux R7.U9
    - Download and upload them into VMware datastore
- Completely create two VM normally till it up and running
    - Yum upgrade for latest packages and setup NTP server (*)
- Prefered use external Network (first Distributed vSwtich).
    - So you can `ssh` into VM for better remote experience
    - If not, use VMware Web/Tools console UI

After that, start config
- Setup two share disk with Thick + Eager zerod provision with multi-writer sharing mode
    - Setup two new disk in OracleRAC-01
    - Adding them into OracleRAC-02
- Add the seconed Distributed vSwtich
    - As you already have External network on the first step, We now add the private network for internal connection beetween two Oracle RAC server

#### OS config

> (*): For even restricted environment, this notes lab have full offline install

Adding ISO as local repo

Setup local NTP server

Disable firewall

Set up these two diferent network interface
- ens<#1> -> 192.168.10.0/24: For privated/internal
- ens<#2> -> 192.168.11.0/24: For external 

Setup mount directory for shared disk

#### RAC config

