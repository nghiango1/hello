# oracle

Or Oracle DBMS, this contain note for possible Oracle database administrator


## Notes

### Data protection

#### Oracle RAC

Normally, we using single run architecture, which mean only one server is running, and backup get activated if running server go down. Oracle RAC have all the server up and running (or we can said Active:Active mode)

Technical infomation:
- Oracle RAC can be seperated by two components
    - Shared storage space
        - Should use no RAID (or RAID-0)
    - RAC server
        - Should use RAID-5
- NTP is required for Oracle RAC
- DNS is also needed

RAC server:
- Oracle RAC multi server - networking and server deploy:
    - IP: Mean "real" IP of each RAC server
    - VIP: A single (unused) IP endpoint that external connection will connect to. This will then be handle by all RAC server 
    - Connection:
        - RAC server need direct connection beetween each other for Internal communication
        - RAC server need to have external connection - So other can access it

Shared storage space
- Oracle RAC leverage shared diskspace (eg: SAN, NFS, ... or "Cloud" shared storage)
- Oracle RAC then using ASM file system (of Oracle) to manually handle the diskspace (doesn't rely on OS for file handling)
    > ASM is based on the principle that the database should manage storage instead of requiring an administrator to do it. 
    - With some search, it still seem like ASM is file-level optimization, not at disk-level optimization. So it still need OS
- Cell: Shared storage can be split over many "Cell"
    - This also mean a Cell can be malfunction and can't be accessed by Oracle RAC server
    - If this happend, all system will be halt if some data unavailable. Which could be cause because of some file (in the "dead" ceel) isn't mirror and accessable from other cell.
    - You will have to handle and make the "dead" Cell available.
    - To prevent this happend, keept storage utilized lower than 70%. Making ASM handle file mirror optimally
    - Best practice: We should use 2 Cell

#### Data guard

"Clonning" data from DC to DR infrastructure. Mode avaiable
- Batch size clonning: After reach a threshold (eg: 4GB data size), DC will have to be stop and data being pushed to DR
- Smart clonning - "Best of both world": Only push data when the System have low load (Oracle handle automatically)
- Real-time clonning: DC data and DR data much be the same, making the system halt until all writing being done in both DC and DR

### Tool for move DB in oracle

> Read more in `moveOracleDB/`

### Plugable database

> Read more in `PlugableDatabase/`
