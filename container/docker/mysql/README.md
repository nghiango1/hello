# mysql

Docker sql instance

## Start a mysql server instance

```sh
sudo docker run --name mysql -e MYSQL_ROOT_PASSWORD=example -d -p 3306:3306 mysql #or mysql:tag
```

Options explain

- `some-mysql` is the name you want to assign to your container,
- `example` is the password to be set for the MySQL root user -> Which isn't that great, we expose our root db infomation in the command here
- and `tag` is the tag specifying the MySQL version you want. If we not address that, it should be 'mysql:lastest' as default

Supported tags and respective Dockerfile links

- 8.3.0, 8.3, 8, innovation, latest, 8.3.0-oraclelinux8, 8.3-oraclelinux8, 8-oraclelinux8, innovation-oraclelinux8, oraclelinux8, 8.3.0-oracle, 8.3-oracle, 8-oracle, innovation-oracle, oracle
- 8.0.36, 8.0, 8.0.36-oraclelinux8, 8.0-oraclelinux8, 8.0.36-oracle, 8.0-oracle
- 8.0.36-bookworm, 8.0-bookworm, 8.0.36-debian, 8.0-debian

Kill and remove the containter when finshed using

```sh
sudo docker kill mysql
sudo docker rm mysql
```

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
    ports:
      - 3306:3306 

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

### Understanding swarn stack deployment

Thing to look at:
- `db`, `adminer` mean services's (container's) network name, which can be use to access that service of the swarn deploy.
- `posts` is similar to `-p` flag, that expose a port of the container to the host machine

This mean when using adminer (from our created `mysql` swarn), in the server field, we can use `db` directly instead of mysql server IP address when using the `adminer` WebUI.

This isn't hold true when interacting with the same service name from other swarn deploy. For example, I start a new swarn `postgres` with `db` service is bind to postgres database server. This time, when I reuse adminer in `mysql` swarn and try to connect to created postgress using `db` as server address, adminer WebUI can't find and connect to the postgres server as it (`db`) resovle to a different IP address.

Stop/remove `mysql` stack deployment

```sh
sudo docker stack rm mysql
```

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
