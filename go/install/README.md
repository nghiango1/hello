ref: https://github.com/moovweb/gvm

This install binary file to start
```sh
sudo apt-get install bison
bash < <(curl -s -S -L https://raw.githubusercontent.com/moovweb/gvm/master/binscripts/gvm-installer)
gvm install go1.4 -B
gvm use go1.4 --default
```

This compile the code (go require go to compile)
```sh
gvm install go1.17.13
gvm use go1.17.13
gvm install go1.21.1
gvm use go1.21.1 --default
```
