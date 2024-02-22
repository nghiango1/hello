# Moving Oracle DB to other Machine

## Prerequired

We set that DB from Machine A (sent) being move to Machine B (receive)
- Check DBMS version, B need to be higher than A
- What to move, do you want to change DB name

Tool
- EXP: 21c, 23c (Have been discontinue)
    - Need create DB first, import data later
    - Help export data from client, (so user need export permision on Machine A)
    - Will not export table that have no rows
- EXPDP: From 10g, 19c
    - Need create DB first, import data later
    - Help import data from server
- RMan: (Best)
    - Need A and B have the same version
    - If A or B not have the same version, you still need to update the version after move DB

## Related infomation

### EXP, EXP [D]ata [P]umb
You will need to create the same list of Tablespace of A on B
User 
- Schema 
    - Table
        - Table space
            - System
            - Sys
            - Sysaux
            - Undo
            - Temp

Change user name: (eg: User1 -> User2) Need to config when using the tool
- EXP: Will need you create User beforehand
- EXPDP: Will let you create user if it not created yet

### Tool instalation

It come with Oracle DB 19c Client (client_home) (Windows)
- Unzip and setup inplace
- Check installation
    ```cmd
    exp help=y
    ```

## EXP

- `USERID`: User2. Login string for server
- `FILE`: Dump file name
- `OWNER`: User1, if not specify, it will dump all User2 tablespace
- `INDEXES`: Set indexes = N. And re-index later for faster Dump
- `TABLESPACES`: List all table space (that we will use for export)

### Example

`exp.par`
```par
userid=admin/admin@192.168.10.70/pdb_...
file=qlcv.dmp
log=qlcv.log
owner=(qlvc,qldt)
index=n
```

Run `exp` with created config
```cmd
exp parfile=exp_qlcv.par
```


## EXP Data Pump 10g

Need to create data pumb dump direcoty
```
sqlplus admin/admin@102.../pdb_...
> create or replace directory backup as '/u01/app/oracle/app/'
```

Start
```
expdp help=y
```

- `CLUSTER`: `yes/no`, all Oracle RAC server need to see the directory
- `DATA_ONLY`: 
- `INCLUDE`:
- `EXCLUDE`:
- `QUERY`: Back up specifice `WHERE a > 10`
- `REMAP_DATA`: Change column name
- `REUSE_DUMPFILES`: `NO` will force to user handle same name dump file
- `TABLESPACE`: List tablespace will be export

### Example

??

## LAB

### Install database
### Try backup
