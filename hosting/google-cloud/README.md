# Google cloud platform

I quite interest in their [free tier](https://cloud.google.com/free/docs/free-cloud-features#free-tier-usage-limits) compute with 30 standand strorage unit. Which in full form:
- 1 non-preemptible e2-micro VM instance per month in one of the following US regions:
    - Oregon: us-west1
    - Iowa: us-central1
    - South Carolina: us-east1
- 30 GB-months standard persistent disk
- 1 GB of outbound data transfer from North America to all region destinations (excluding China and Australia) per month

But if have some limit on the network to only 1 GB. Thus this storage is only for running app basically. This is quite perfect for my [InterinGo project](../../go/interpreter/)

This is me setting up Google cloud VM instance while checking their price model and keep an eye on what their Free tier offer

## Compute engine

Create a new VM without opening it have no cost. Also you can have multiple instance ready, just opening and running them that will be charged. Free-tier will allow to deduce the total time of that month vs the total time that you use (total time of every VM e2-micro instance running):

Create [Compute](https://console.cloud.google.com/compute) engine expose you to 2 other importain concept / with a seperated price model:
1. Compute storage ([Disk](https://console.cloud.google.com/compute/disks) and [Snapshot](https://console.cloud.google.com/compute/snapshots)): VM have their boot disk, which is changeable any time. This mean that you can kept the boot disk as a seperated entity (and it is in-fact being managed seperately in Google cloud) and push them into new VM instead of re-installing OS every time (if you not using Alpine Linux which can run entirely on RAM with minimal cost) and copy over your work data/ or deploying your application.
    - Kept disk when delete VM: We can disable "Delete disk" option when delete VM to make sure our free-tier disk is kept
    - Storage type: Which we forcus on how to setup or change our exsiting disk to standand type for 30 Gb free tier offer
2. [Networking](https://console.cloud.google.com/networking): We is allowed to use within 1 Gb of data tranfered to external network for free from Google Free-tier offered (except for connection from China and Australia)
    - Creating **static** external IP for accessing VM: Normally you will be assign a random external IP then lost it after turn on, off your VM. Google cloud will need you to Create static external IP then re-assign it to the VM to prevented this to happend
    - Cost for outbound/external network (Global)
    - Cost for outbound/external network (Regional)
    - Network type: Premium vs Standand
    - (Maybe) Block China and Australia connection

> While the UI to create VM (Compute engine) could help you quickly create disk and static external IP, my strongly recommend is to go to thier coresponsed UI instead.

## Compute storage

### Changing Balance persitance disk to Standard persistent disk

> When you start a VM, it is default that your boot disk is a Balance persitance disk. So, this is how to change it back

Naturally, it have a guide from [support page](https://cloud.google.com/compute/docs/disks/modify-persistent-disk#gcloud). Which tell you to do these 2 step
- Create a snapshot of your disk
- Create a new persistent disk based on the snapshot

Step 1 - Create a snapshot of the disk
- Go to Google cloud Web UI - Snapshot
- Press Create
- Setup snapshot with standard type
    - **Extra:** you could also set up region for the snapshot so try to set it to the location where

Step 2 - Create new disk base on the snapshot
- Go to Google cloud Web UI - Disk
- Choose your disk which need to switch type

Step final - Recheck your created disk into a VM instance
- Is it bootable. Can you use it as boot disk for a VM instance then Running it
- Check if all your file is still there

### Pricing

Disk cost the entire presevered capacity, which mean you create a 30GB disk and you will be charge for 30GB of storage cost

Snapshot is a bit better, you only need to pay the actual compressed disk used (Which with only os-boot-able data should be around 1~2Gb) instead of the original Disk full size

### Limit

There is a hard limit that you can create a new Disk: Minimum and maximum disk size, which is custom base on each disk type and disk located region. But most will allow Standand disk (without replica) type to be create at 30 GB size

> This affecting us when trying to chose our VM locate region, which then also affect our Network pricing and available network type (which also base on region)

## Network

### Reserving internal/external IP address

When you turn off the VM, you also releasing that VM IP. This may be a problem (like you using DNS recorded to bind IP with a domain name, either for internal or external usage) so you may want to reserving a static IP address.

This do cost extra as in their pricing model **if you not using IP for any running VM**

### Network type

There is specific region that we can use a lower-cost [Network standand type](https://cloud.google.com/network-tiers/docs/overview#regions_supporting_standard_tier). Luckly, it contain all of the 3 free-tier avaiable regions: us-west1, us-central1, us-east1

### **[Extra]** Block China and Australia network request

I use cloudflare, which allow to set WAF rule. I then add rule per domain that I own pointed to this current free-tier-instance and block any request with Contry from China or Australia. This is unrelated from Google cloud at the moment.

Also, this could be done by setup my HTTP server, which is currently Nginx. I should look further into this as I also want it to stop sending out responesed to prevent network limit hit.
