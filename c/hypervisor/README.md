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

Load built module to Kernel

```sh
sudo insmod hypervisor.ko
```

Read debug message that being `printk` in the code

```sh
sudo dmesg |tail
```

# Note

## `__asm__ __volatile ( Assembler : Out : In : Clobber )`

This appear a lot in the code as we working with hardware and need specific instruction base on exact bit said in Intel x86 chip manual

Document for `__asm__` is available at this link
https://gcc.gnu.org/onlinedocs/gcc/Extended-Asm.html


