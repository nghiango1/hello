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

```
sudo docker ps
```

- or just all containner that been pulled

```
sudo docker ps -a
```

- or just a latest container

```
sudo docker ps -l
```

## Inspect containner

Using container-id, 

```
sudo docker inspect <container-id>
```

- Or using this for latest

```
sudo docker inspect $(sudo docker ps -q -l)
```

