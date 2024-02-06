# lrucache

For a project `c` language (also to understand more about lrucache implement)

## Notes

`Makefile` is the configuration file for `make` tool, which is a helper program I used for building `c` language project. While not fully understand all the feature of make, here is what I learn
- I can create a variable using `:=` operator and use it by using `$(<variable_name>)`
    ```
    CC := gcc
    ```
- I can create a command, and run it using `make <command>` (which similar to `script` in `package.json` and `npm run <command>`). There is a special `all` command, which will be run as default when calling `make`
    ```make
    all: <do x, do y>
    clean: rm -rf dist build
    ```
- I can specify a command which will call to other command to combine them
    ```make
    all: dist build lrucache
    dist: mkdir -d dist
    build: mkdir -d build
    lrucache: gcc ...
    ```
- I can change all hardcode value to variable
    ```make
    CC := gcc
    TARGET := lrucache

    $(TARGET): $(gcc)...
    # this equal to lrucache: gcc ...
    ```
