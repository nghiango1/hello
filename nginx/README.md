# nginx

It can be use as a reversed proxy for multiple deploy application on the same machine.

## Notes

Install nginx
```sh
sudo apt install nginx
```

Check install complete
```sh
systemctl status nginx
```

Config file at `/etc/nginx/nginx.conf`, to serve a new reverse proxy, we can add new file in `/etc/nginx/conf.d/`. Here I create a new virtual server for `jekyll.local` as example.
- Normally, we used `bundle exec jekill serve` which create a server at `127.0.0.1:4000` which is local only (not public to other network interface).
- We then config a new server in `nginx` config file `jekyll.conf`
    ```nginx
    server {
        listen 80;
        server_name jekyll.local; 

        location / {
            proxy_pass http://localhost:4000;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Host $http_host;
            proxy_set_header X-NginX-Proxy true;

            # Enables WS support
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection "upgrade";
            proxy_redirect off;
        }
    }
    ```
- On other client, we can add our virtual server localtion in local DNS `/etc/hosts`, and goes to `jekyll.local` URL directly in browser.
    ```hosts
    10.243.143.44 jekyll.local
    #^^^^^^^^^^^^ Use your own host ip
    ```
- We can repeat this process for multiple application instance on the same machine with different `server_name` and `port` sharing the same IP address
