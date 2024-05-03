ref: https://github.com/moovweb/gvm

# `go` install

I prefer using a management tool to use and switch beetween go version.

## Install `gvm` - Go version manage tools

```sh
sudo apt-get install bison
bash < <(curl -s -S -L https://raw.githubusercontent.com/moovweb/gvm/master/binscripts/gvm-installer)
```

## Using gvm to install go

```sh
gvm install go1.22.2 -B
gvm use go1.22.2 --default
```

## Build `go` executable from source

> Start from go1.4 go source need to build from go

This install binary file to start

```sh
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

## Download `go` executable

```sh
gvm install go1.21.1 -B
gvm use go1.21.1 --default
```
