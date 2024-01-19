# Cland LSP helper

While using Clang LSP, normal project can be quite easy to setup without any library. But this not the case dealing with complex already setup project. To make our life easier we can use `bear` command to auto generate cland config for LSP

```sh
sudo apt install bear
bear -- make
#       ^^^^ - This can change to your build command
```

Other than that just delete some buged line where cland didn't understand

# Hypervisor

I follow the Nixhacker serial on developing hypervisor from scratch

Part 2 link:
https://nixhacker.com/developing-hypervisor-from-scratch-part-2/

This is that for me to understand more about virtualization and hardware support in general, where we trying to implement new Private Cloud Infrastructure

Build

```sh
make
```

Implement
