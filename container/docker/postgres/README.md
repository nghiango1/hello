# posgresql

Docker sql instance

## Compose container depploy

You want to runing both:
- a posgresql DB instance
- an admin server to manager posgresql DB


Runing stack deploy on compose file
```sh
sudo docker stack deploy -c docker-compose.yml posgresql
```

Expected output

```
Ignoring unsupported options: restart, shm_size

Creating network posgresql_default
Creating service posgresql_db
Creating service posgresql_adminer
```

Access admin server via [http://`<any-address>`:8081/](http://127.0.0.1:8081/) with postgres/example

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
