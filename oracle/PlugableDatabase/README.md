# Pluggable database

A database can be seperated from Oracle DBMS, with it own set of user, local user, ect

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
    akter pluggable database PBDPDB1 save state instances=all;
    ```

Using CLI `sqlplus`
- Login with `sqlpus`
    ```bash
    $ connect sys/<password> as <role>/sysdba
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
    ```
    alter session set container=PDB_PDB1;
    ```
- Recheck
    ```
    show pdbs
    # This now should return only one PDB_PDB1
    ```
- Back to `root` (normal session)
    ```
    alter session set container=cdb$root;
    ```

### Create user

Create a new user for all pdb
- Command: container is set to all so that user will be created in all pdb
    ```
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
    ```
    alter session set container=PDB_PDB2;
    grant dba, resource, connect to c##<username>;
    ```
    or we can grant all
    ```
    grant dba, resource, connect to c##<username> container=all;
    ```
- Recheck
    ```
    $ connect sys/<password> as <role>/sysdba
    ```

