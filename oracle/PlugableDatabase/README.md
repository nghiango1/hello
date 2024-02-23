# Pluggable database

A database can be seperated from Oracle DBMS, with it own set of user, local user, etc..

## Notes

> All commanline in this session is from `SQLPLUS` tool, not in shell/bash environment. Unless the command start with `$`

### Create pluggable database
Create Pluggable database (pdb) using `dbca` GUI Tool
- Choose Create Pluggable database
- Unplug database: We can move a DB from a Oracle DBMS to anoter DBMS
- Select database
- Create a new Pluggable DB can be clone from other PDB
    - We should use this, and go with default pdb `PDB$SEED`
    - Other than that, craete it from an unplug db
- Set administrator username/password
- You can tick at create default user tablespace
- Done
- Recheck
    > We can list Pluggable database using `SQLPLUS` cli
    ```sqlplus
    show pdbs
    ```
- Save the state of newly created PDB (so that it will be open with the same mode, eg "READ WRITE", when DBMS start)
    ```sqlplus
    alter pluggable database PBDPDB1 save state instances=all;
    ```

Using CLI `sqlplus`
- Login with `sqlpus`
    ```sqlplus
    connect sys/<password> as <role>/sysdba
    ```
- Create
    ```sqlplus
    create pluggable database PDB_PDB2 admin user <admin_username> identified by <password> default tablespace users datafile size 1G;
    ```
- Recheck
    ```sqlplus
    show pdbs
    # #Example outout:
    # CON_ID  CON_NAME    OPEN MODE   RESTRICTED
    # ------  --------    ---------   ----------
    #    ...
    #      5  PDB_PDB2    MOUNTED
    ```
- Open the pdb
    ```sqlplus
    alter pluggable database PDB_PDB2 open;
    ```
- Save the stage
    ```sqlplus
    alter pluggable databse PDB_PDB2 save state instaances=all;
    ```

If you not done save the state, restarting instance will not open pdb created
```sqlplus
instance shut down
instance started
```

Focus seession container, this will ensure we just working with one PDB
- Command
    ```sqlplus
    alter session set container=PDB_PDB1;
    ```
- Recheck
    ```sqlplus
    show pdbs
    # This now should return only one PDB_PDB1
    ```
- Back to `root` (normal session)
    ```sqlplus
    alter session set container=cdb$root;
    ```

### Create user

Create a new user for all pdb
- Command: container is set to all so that user will be created in all pdb
    ```sqlplus
    create user c##<username> indentified by <username> contaner=all;
    ```
    - Common user
        - The username for common user must be prefix with c##
        - username must be unique across all container
        - DEFAULT TABLESPACE, TEMPORARY TABLESPACE, QUOTA, PROILE
        - `container=all` can be specify or not, both will grant as root (all pdb)
    - Local user
        - Must login to container with a user that have `CREATE_USER` roles
        - All other is the same
- Grand roles, here we can go to specific pdb and set the role
    ```sqlplus
    alter session set container=PDB_PDB2;
    grant dba, resource, connect to c##<username>;
    ```
    or we can grant all
    ```sqlplus
    grant dba, resource, connect to c##<username> container=all;
    ```
- Recheck
    ```sqlplus
    connect sys/<password> as <role>/sysdba
    ```

### Example

Connect to RAC instance with `sqlplus`
```bash
$ sqlplus / as sysdba
```

Create pluggable database
- We can recheck every step with
    ```
    show pdbs
    ```
- Create new pdb
    ```
    create pluggable databse pdb_pdb1 admin user admin identified by admin defaul tablespace user datafile size 1G;
    alter pluggable database pdb_pdb1 open;
    alter pluggable database pbd_pdb1 save state instances=all;

    show pdbs
    ```
    We should se READ WRITE state PDB_PDB1 now from `show pdbs` output, along with oother PDB
- Change session to newly created PDB_PDB1
    ```
    alter session set container=PDB_PDB1;

    show pdbs
    ```
    We should only see PDB_PDB1 now from `show pdbs` output
- Create new table space (that match with what about to clone db)
    ```
    create tablespace TBS_FILE datafile size 1G;
    create tablespace TBS_QLCV datafile size 1G;
    ```
- Create user
    ```
    create user c##trongnv indentified by trongnv contaner=all;
    grant dba, resource, connect to c##trongnv;
    ```
- Get data from `moveOracleDB/README.md` Example LAP
- Import data
    ```
    impdp userid=c##trongnv/admin@192.168.2.45/pdb_pdb1 dumpfile=qlcv.dmp logfile=qlv.log directory=backup cluster=N status=100
    ```
    - We use `sqlplus` on `rac-19c-01` as our client
        - In RAC environment, sometimes we use SCAN IP to connect to RAC instance, which then forward us to RAC1 or RAC2
        - Which may casuse directory become invalid, as we have dump file in RAC1
    - `<ip>` -> 192.168.2.45: here should be the specific machine that have backup directory
    - For current LAP, thing done in `rac-19c-01` machine, with ip 192.168.2.45


## GUI tools

We can get all user tablespace with `get_owner_tables.sql`
```sql
SELECT DISTINCT TABLESPACE_NAME FROM DBA_TABLES WHERE OWNER=UPPER('<name>');
```

### Using `Toad` for PDB administation

Database -> administration -> tablespace

Create tablespace can be  done from Toad GUI
- General: Set Big file/Small file, Block Size
- Size 1 GB

### Using `PL/SQL developer` for PDB administation

We can use Compile Invalid Objects to check for errors
