# Install Docker

Read the doc

# Using docker

## Run a sample container

This allso install/pull container

```
sudo docker run hello-world
```

## Listing all container

- That running:

```sh
sudo docker ps
```

- or just all containner that been pulled

```sh
sudo docker ps -a
```

- or just a latest container

```sh
sudo docker ps -l
```

## Inspect containner

Using container-id, 

```sh
sudo docker inspect <container-id>
```

- Or using this for latest

```sh
sudo docker inspect $(sudo docker ps -q -l)
```

