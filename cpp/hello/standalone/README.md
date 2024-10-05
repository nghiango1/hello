# Hello

> World!

## Build

Build process of c++ isn't that much different from c. we use `-o` to direct build file output

```sh
g++ hello.cpp -o hello
```

The built file will start at `main` function. If by any change we missing it, the compiler will throw error:
```
/usr/bin/ld: /usr/lib/gcc/x86_64-linux-gnu/11/../../../x86_64-linux-gnu/Scrt1.o: in function `_start':
(.text+0x1b): undefined reference to `main'
collect2: error: ld returned 1 exit status
```

> Or do we? look at [modular hello](../modular)

## Debug

c++ does support debuging natively for the compiled file, but we have to additionally control the output with:
- To preserve the debug symbol infomation, feed in `-g` flag (crutial)
- To prevent compiler optimizer (which can replace our code with binary code - assembly level optimization or some sort of code line swap, ...), feed in `-O0` flag (default value without specify is `-O2`)

```sh
make debug
gdb hello
```

When we doesn't have IDE or fancy GUI, we can use `gdb` Commandline to debug the built file
- l - show the code
- r - run the program
- b <line/function name> - set breakpoint at that location
- c - continue the program
- n - jump next
- s - step into next
- i local - show variables
- i frame - show stack tree and thread
- i args - show the current function agrument
