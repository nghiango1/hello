# array

A sercurity lap on `c` compiling program with array variable

## Notes

This isn't some normal way to use array variable in other language. `c` have a compiling and runtime memory structure that allowing **Memory overwriting**
- Given a array as a pointer to memory
- Write function that lead to alter overbound memory of that array (most of time Attacker will try to overiten execute-able partition in runtime for remote code execution)

> There is some memory cast and writing into array also in here

You will want to use `gdb`, `ida`, `windbg`... instead of normal debugger. Look closely into the compiled executable file and memory. What to look for while debugging this code:
- String array "Helloworld": A initiation string array will be unalterable (or `read` patition of the executable)
- Variables: All will be a big chunk of memory being given at the start of the program (`write` partition)
- Code: Being compile into machine code (CPU instructions), which can be translate to assembly (`execute` partition). Try to look out how writing overbound of array variable could cause the program malfunction, and you will find the segment fault seem way better than having the program keep running

Most of the time it good to find even more build flag and how it affect the build executable file too. If you are that deep into the `c` security world
