# Array

I stumble upon this question: "Is it possible to get the lenght of a array in that have been send as ref in a function?". My answer was "Yes" and here is me try to prove how that possible.

## Thought and knowledge check

Ofcourse this isn't some normal way to use array variable `sizeof`. It just get decay to a pointer and lost it size.

```cpp
    long long arr[4];
    length = sizeof(arr) / sizeof(long long);
```

This clearly said in some random stackoverflow post "Yu KaN't dO It iN cPp" but I'm in disbelieve. C/cpp can't do it, what year is this, am I suppose to build application in Assembly and readding chip instruction or what. Wait, what about heap alocation? Can we free the memory inside a different function.

```cpp
func freeArray(long long arr[]) {
    free(arr);
    // The best practice way to stop free a variable twice, we not do want to
    // start a CTF pwn chalenger here, I just leeting you know
    arr = NULL;
}

int main() {
    arr = (long long*) malloc(sizeof(long long) * 4);
    arr[0] = 1;
    arr[1] = 2;
    arr[2] = 3;
    arr[3] = 4;
    freeArray(arr)
    return 0;
}
```

You actually can, which mean c++ do know in the runtime how long the memory was alocated. This is my theory, we can alway recovered an alocated malloc heap size from the varible pointer, what ever the case.

## Building block

Now, how exactly can we dig that infomation out. So I need a good old friends, CTF PWN tutorial for runtime C/C++ executable (more of a crypto guy my self back in the security team). Let look at this [promissing link](https://www.slideshare.net/AngelBoy1/heap-exploitation-51891400).

![Screenshot.png](Screenshot.png)

I don't even know about chinese but there they are, a chunk size. So basically, memory is alocate by `malloc()` will be handle by heap chunk, which have a maximum size (may be of 128KB from previous slide). By usng chunk header, we can point them to the next chunk or end of linked chunk (linked list mention!!).

There is more to it, like: Large bin, small bin, fast bin, unsorted bin; which just to make memory fragmentation less of a problem but we will skip for now and raise these again when needed.

It will be way easier when the code actually working and running. I can look at alocated memory in runtime directly. But can `c++` have in language API to support runtime infomation extracting. It turn out to these function that we can look into how we can navigate the chunk after I found out a post that exlained throughly how glibc `free()` work with chunk.
- `mem2chunk` is simple enough, point backward from the pointer, cast it into a chunk header structure. It seem appear in glibc source code `malloc.c` file, and I can't access that. A non public function.
    ```cpp
    #define mem2chunk(mem) ((mchunkptr)tag_at (((char*)(mem) - CHUNK_HDR_SZ)))
    ```
    We can re-implementing it tho, simple enough
- `is_mmapped` is some flag check to handle chunk, not thing fancy, we can do it our self after understanding our chunk header object.

Here is the exact quote from the site I found
> Long story short, [free](https://sourceware.org/git/?p=glibc.git;a=blob;f=malloc/malloc.c;h=e065785af77af72c17c773517c15b248b067b4ad;hb=ae37d06c7d127817ba43850f0f898b793d42aea7#l3237) works like the following. When it is called, the user would pass a pointer to the memory area to it, free would then call [mem2chunk](https://sourceware.org/git/?p=glibc.git;a=blob;f=malloc/malloc.c;h=e065785af77af72c17c773517c15b248b067b4ad;hb=ae37d06c7d127817ba43850f0f898b793d42aea7#l1310) to convert the pointer to point to the chunk header. Then, if the chunk is allocated by mmap indicated by the M flag, free calls munmap ([man 3p](https://man.archlinux.org/man/munmap.3p.en) | [man 2](https://man.archlinux.org/man/munmap.2.en)) to release the chunk; if not, it passes the chunk pointer to [_int_free](https://sourceware.org/git/?p=glibc.git;a=blob;f=malloc/malloc.c;h=e065785af77af72c17c773517c15b248b067b4ad;hb=ae37d06c7d127817ba43850f0f898b793d42aea7#l4302) for the actual freeing process.

Now before I try to implement the thing, just to make sure, let find a package that does just that first. I'm using a Ubuntu Jammy (22.04LTS) on x64 (VM) computer for reference. And this is [it?](https://packages.ubuntu.com/jammy/devel/glibc-source)

```sh
ldd --version
# ldd (Ubuntu GLIBC 2.35-0ubuntu3.6) 2.35
# ...
sudo apt install glibc-source
```

Well, getting no where out of that, so I just get the source code

```sh
git clone -b ubuntu/devel https://git.launchpad.net/ubuntu/+source/glibc
```

Let see.

## Implementation PoC

> `array.cpp` is there, complile it and check that out.

A Prove of Concept - PoC mean I can prove it possible via an example, but it not in a general way and can be use in any situation, I get this term from those cvs exploit hacker guy articles and do hope you undertand what I mean here. With that in mind, let start with a chunk structure in glibc `malloc.c`

```c
struct malloc_chunk {

  INTERNAL_SIZE_T      mchunk_prev_size;  /* Size of previous chunk (if free).  */
  INTERNAL_SIZE_T      mchunk_size;       /* Size in bytes, including overhead. */

  struct malloc_chunk* fd;         /* double links -- used only if free. */
  struct malloc_chunk* bk;

  /* Only used for large blocks: pointer to next larger size.  */
  struct malloc_chunk* fd_nextsize; /* double links -- used only if free. */
  struct malloc_chunk* bk_nextsize;
};

typedef struct malloc_chunk* mchunkptr;

#define mem2chunk(mem) ((mchunkptr)tag_at (((char*)(mem) - CHUNK_HDR_SZ)))
```

In this code, we have notthing fancy going on, just to make clear

- INTERNAL_SIZE_T is (as default) size_t, a `unsigned long` number type to contain the length in bytes of a structure/object. The `malloc.c` use a `#define` value here so that we can change it in a specifial case that you want to use `unsign long long` instead.
- `mem2chunk` will point back to a `CHUNK_HDR_SZ` (chunk header size) and cast it to a `mallock_chunk` pointer.
- `tag_at` is unclear, I really don't see how that related to any of the implementation
- It still un-clear for me what is `CHUNK_HDR_SZ` value are

So I can't use them exactly in the code, but let get it close enough.

Can't do anything yet. I check the memory in a runtime using `gdb` debuger to find where my chunk header are. `0x5555555596b0` is the address of (function decay) array, jump it back 100 byte

Which is 

```
(gdb) i arg
arr = 0x5555555596b0
chunk_hdr_sz = 10
(gdb) f 1
#1  0x00005555555554b9 in main () at array.cpp:89
89        getSize(arrPrt, chunk_hdr_sz);
(gdb) i local
arr = {0, 0, 0, 0}
arrPrt = 0x5555555596b0
arrPrt2 = 0x5555555596e0
chunk_hdr_sz = 10
p = 0x5555555596a6
(gdb) x/40x arrPrt-8
0x5555555596a0: 0x00000000      0x00000000      0x00000031      0x00000000
0x5555555596b0:[0x00010000      0x00030002      0x00050004      0x00070006 # arrPrt start here
0x5555555596c0: 0x00090008      0x000b000a      0x000d000c      0x000f000e]# arrPrt end here
0x5555555596d0: 0x00000000      0x00000000      0x00000051      0x00000000
0x5555555596e0:[0x00010000      0x00030002      0x00050004      0x00070006 # arrPrt 2 start here
0x5555555596f0: 0x00090008      0x000b000a      0x000d000c      0x000f000e
0x555555559700: 0x00110010      0x00130012      0x00150014      0x00170016
0x555555559710: 0x00190018      0x001b001a      0x001d001c      0x0000001e]# arrPrt 2 end here
0x555555559720: 0x00000000      0x00000000      0x000208e1      0x00000000
0x555555559730: 0x00000000      0x00000000      0x00000000      0x00000000
```

Now I asumming that our header should be in between both pointer. Which make these are the chunk header

```
0x5555555596a0: 0x00000000      0x00000000      0x00000031      0x00000000
0x5555555596d0: 0x00000000      0x00000000      0x00000051      0x00000000
// There is supicous value after both pointer
0x555555559720: 0x00000000      0x00000000      0x000208e1      0x00000000
```

We only focus in those that have a value. Changing arrPrt2 to 64 + 32 byte change some byte too
```
0x31 == 0b00110001
0x51 == 0b01010001 -> 0xd1 == 0b11010001

// There is supicous value after both pointer
0x000208e1 == 00000000000000100000100011100001
->
0x00020861 == 00000000000000100000100001100001
```

```
16      == 0b00010000 == 0x10
64 + 32 == 0b01100000 == 0x60
```

Can't really make any sense of this :'). Gave up time

## Final take

I covered PoC for Heap. Now about stack, if we can by any change, [expanding or trackback call stack](https://www.gnu.org/software/libc/manual/html_node/Backtraces.html) then run the `sizeof()` there. I do think that a general getting array length native support function implement into glibc is possible. Until then, the answer for title question is a solid "It depend!".

Yeah, may be I should said that "Yes" with an overconfident voice. But you see, if someone said you can't do X, have some re-thought about that and use it to ecourage your self digging for the answer from the root instead of blindingly distribute them to others people.

> Expecially the source come those internet site that soonner and later will be replace by AI. Not like any of those gonna give a better answer but sill.

Source for relevance link that I get infomation from, kudo to the authors:
- https://shuye.dev/blog/malloc_chunk/
- https://www.slideshare.net/AngelBoy1/heap-exploitation-51891400
