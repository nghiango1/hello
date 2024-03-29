# Singleton

Who isn't hate this patern

## The problem

You have only one object of this class avaiable for entire runtime. Until use want to make it lazy created and every other function start a race condition to create the object.

- Better Init it in a single thread then
- Block everything until a thread finish changing Singleton object

But Singleton is the way to help with memory management, why?
- If you not care about memory just please using all static property instead, it is way safer and done the same thing.
- By using singleton, we make sure that if it is the case that we don't need the object, only some static properties contain pointer is init and use in the system, instead a whole big chunk of memory already alocated at the start of the progam.

So Init it in a single thread at start of the program should not be the case. But after start program init, creating Singleton object in only one thread is easier said than done in multi-thread progam.

This is a strict case that we want to do Singletion - Parallel - Lazy instance initiation

## What this aim for

Java tool chain is really mature, so I want to know what Java developer environment give to developer to debug a progam
- DAP
- LSP
