# Getting started

```sh
podman run -dt -p 8080:8080/tcp -e HTTPD_VAR_RUN=/run/httpd -e HTTPD_MAIN_CONF_D_PATH=/etc/httpd/conf.d \
                  -e HTTPD_MAIN_CONF_PATH=/etc/httpd/conf \
                  -e HTTPD_CONTAINER_SCRIPTS_PATH=/usr/share/container-scripts/httpd/ \
                  registry.fedoraproject.org/f29/httpd /usr/bin/run-httpd
podman ps
```

You will want to focus

- `-d` : Detacted mode, container running in background
- `-e` : Adding environment variable, this seem to need check httpd to know
    ```
    -e HTTPD_VAR_RUN=/run/httpd
    -e HTTPD_MAIN_CONF_D_PATH=/etc/httpd/conf.d
    -e HTTPD_MAIN_CONF_PATH=/etc/httpd/conf
    -e HTTPD_CONTAINER_SCRIPTS_PATH=/usr/share/container-scripts/httpd/
    ```
- `-p` : port forwarding, `8080:8080/tcp`

By going to http://<hostip>:8080, example I used my host VPN: `http://10.243.143.44:8080/`, you will get a Fedora Test Page welcome page

# Checking container full infomation

Get container-id with

```sh
podman ps -a
```

Output

```
CONTAINER ID  IMAGE                                        COMMAND               CREATED         STATUS                     PORTS                   NAMES
fca0a6f7a40d  docker.io/library/hello-world:latest         /hello                22 minutes ago  Exited (0) 22 minutes ago                          recursing_lamport
73baeb781494  registry.fedoraproject.org/f29/httpd:latest  /usr/bin/run-http...  19 minutes ago  Up 19 minutes ago          0.0.0.0:8080->8080/tcp  vigilant_noyce
```

Then using it for inspect, incase of lazy, we can used `-l` or `--latest` for latest container

```
podman inspect 73baeb781494
```

Output is quite long, so you want to have some foundation before readding it
