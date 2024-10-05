# Modular

> header file is here

Unlike any language, cpp (and c) require you to provide a header file to tell you which function/class/... is in other file. Thus, we need to create think like this `include/hello.h` which is a doublicate of `src/hello.cpp` like an interface.

WE also have two different way to import hello module into our program `src/main.cpp`

# Pack everything in:

Use `-c` flag and we can create a temporial file build step without compiler error
Use `-I` flag to help the compiler find the header file. It will try to find relative path or default system path by default. As we put our header file into `./include/` directory, we state this infomation to the compiler via `-Iinclude`
```sh
g++ src/hello.cpp -c -o hello.o -Iinclude
g++ src/main.cpp -c -o main.o -Iinclude
```

Use linker `ld` or `g++` (which also call to linker). I don't really use linker so let just focus on `g++` wrapper for linker instead. Combine every `.o` temporary built file from last step into one using this syntax
```sh
g++ -o main build/hello.o build/main.o
```

# External Shared library (dinamicly link):

Use `-shared` flag and we can create a library file without compiler error
```sh
g++ src/hello.cpp -shared -o libhello.so
```

Library name will be force to have this template `lib<lib-name>.so.<version>`, I skip the versioning as I can't seem to find the document for that flag within 10 mins of seaching (why internet, should I need to download the C++ IEEE specification or what). The name `hello` is the actual name for our built library

To use the library, we need header file again, but with some extra control
- If you use `#include "..."` the compiler will search the local directory first and then the system directory.
- If you use `#include <...>` the compiler will search the system directory.

We not install our `hello` library into the system, so kept it `"..."` form is fine.

To build the `main` program, we will need 2 more flag
- Use `-l` to state that the `main` program will need to link with `libhello.so` to be built
- Use `-L` to tell compiler wher to find the library. Similar to `-I` flag, just for library. But it will try to find in system directory only, we state `-L.` to have it find our `hello` library in the current working dir.
```sh
g++ src/main.cpp -o main -lhello -L. -Iinclude
```

This help built the file, but not so much to run the file: A linked to external library program need the library to be able to run (those library versionning will make this a bit harder but oh well). And we need those library to be install in the system or provide alternative path using os variable `LD_LIBRARY_PATH`.
```sh
env LD_LIBRARY_PATH=. ./main
```

> With versionning, we may want to differenciate library for dev name `lib<abc>.so`, library full qualify name `lib<abc>.so.1` and version name `lib<abc>.so.1.23.4`. We need to feed qualify name to run the progam. Dev name `.so` file will be use for compiler. And finally, the versionning name is there to be use for distributing our library.

# External Static library

Compile: Compile all temporary file like normal
```sh
g++ src/hello.cpp -c -o hello.o -Iinclude
```

Create static library: g++ support extract file from `ar` archive file command. Put all temporary file into an achive file and we call that static library. Change library name `lib<abc>.so` to `lib<abc>.a` for namming
```sh
ar r libhello.a hello.o
```

Build: Same with the build
```sh
g++ src/main.cpp -o main -lhello -L. -Iinclude
```

> We will need to have `lib<adb>.a` library file when compile with `-static` flag.
