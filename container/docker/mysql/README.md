# mysql

Docker sql instance

## Start a mysql server instance

```sh
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
```

Options explain

- `some-mysql` is the name you want to assign to your container,
- `my-secret-pw` is the password to be set for the MySQL root user
- and `tag` is the tag specifying the MySQL version you want.

Supported tags and respective Dockerfile links

- 8.3.0, 8.3, 8, innovation, latest, 8.3.0-oraclelinux8, 8.3-oraclelinux8, 8-oraclelinux8, innovation-oraclelinux8, oraclelinux8, 8.3.0-oracle, 8.3-oracle, 8-oracle, innovation-oracle, oracle
- 8.0.36, 8.0, 8.0.36-oraclelinux8, 8.0-oraclelinux8, 8.0.36-oracle, 8.0-oracle
- 8.0.36-bookworm, 8.0-bookworm, 8.0.36-debian, 8.0-debian

## Compose container depploy

You want to runing both:
- a MySQL DB instance
- an admin server to manager MySQL DB

Example compose file from 
```yaml
# Use root/example as user/password credentials
version: '3.1'

services:

  db:
    image: mysql
    # NOTE: use of "mysql_native_password" is not recommended: https://dev.mysql.com/doc/refman/8.0/en/upgrading-from-previous-series.html#upgrade-caching-sha2-password
    # (this is just an example, not intended to be a production configuration)
    command: --default-authentication-plugin=mysql_native_password
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: example

  adminer:
    image: adminer
    restart: always
    ports:
      - 8080:8080 
```

Runing stack deploy on compose file
```sh
sudo docker stack deploy -c docker-compose.yml mysql
```

Expected output
```
Ignoring unsupported options: restart

Creating network mysql_default
Creating service mysql_adminer
Creating service mysql_db
```

Access admin server via [http://`<any-address>`:8080/](http://127.0.0.1:8080/) with root/example

### Some problem along the way

Errors that require docker to be a part of swarn, this can be done via joinning or create new one
```sh
sudo docker swarm init
```

If you have two or more address, it is required to provide which will be used via flag `--advertise-addr`
```sh
# Error response from daemon: could not choose an IP address to advertise since this system has multiple addresses on different interfaces (192.168.2.114 on ens33 and 10.1.1.99 on ens35) - specify one with --advertise-addr
sudo docker swarm init --advertise-addr 10.1.1.99

# Swarm initialized: current node (xrpb882lalz5e3wz4if6nbnsr) is now a manager.
# To add a worker to this swarm, run the following command:
#     docker swarm join --token SWMTKN-1-68qutx6umxj31f8ttj0gmyiq2z88upfp0iz99iuap0a76ngmbk-7yrl9r5xhp2rm6iut5uz1sbp8 10.1.1.99:2377
# To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
```
