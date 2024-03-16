// provides `NULL`, `free`, `malloc`, `size_t`
#include <cstdlib>

// provides `printf`
#include <stdio.h>

// This base on gdb trace result, I'm unclear if it still this value in other
// chip architecture: ARM, MISP, ... or with a different glibc implement x64
#define CHUNK_HDR_SZ 16

// Default use case with 4 bytes size
typedef size_t INTERNAL_SIZE_T;

// Chunk header implement base on malloc.c
struct ChunkHeader {
  INTERNAL_SIZE_T prev_size; /* Size of previous chunk (if free).  */
  INTERNAL_SIZE_T size;      /* Size in bytes, including overhead. */
  struct malloc_chunk *fd; /* double links -- used only if free. */
  struct malloc_chunk *bk;
};

// This get the chunk header from a memory address
struct ChunkHeader *mem2chunk(void *mem) {
  if (mem == NULL) {
    return NULL;
  }
  return (ChunkHeader *)((char *)mem - CHUNK_HDR_SZ);
}

// This return alocated data length from malloc
INTERNAL_SIZE_T arrlen(short arr[]) {
  ChunkHeader *p = mem2chunk(arr);
  // Mark with value 0b11...11000
  INTERNAL_SIZE_T mark = (~0) ^ (1 + 2 + 4);

  // Try to delete the first 3 bit using our crafted mask
  INTERNAL_SIZE_T chunksize = p->size & mark;
  // Get the final data size of the chunk, the array length in bytes
  INTERNAL_SIZE_T arrayLength = chunksize - CHUNK_HDR_SZ;

  return arrayLength;
}

void arrayLenghtPoC(short arr[]) {
  printf("arr pointer (function decay) size %zu\n", arrlen(arr) / sizeof(short));
}

int main() {
  short *arrPrt;
  arrPrt = (short *)malloc(sizeof(short) * 16);
  for (int i = 0; i < 16; i++) {
    arrPrt[i] = i;
  }

  // Sizeof can't do anything
  printf("arr pointer (sizeof) size %lu\n", sizeof(arrPrt));

  ChunkHeader *p = mem2chunk(arrPrt);
  // Mark with value 0b11...11000
  INTERNAL_SIZE_T mark = (~0) ^ (1 + 2 + 4);
  // Try to delete the first 3 bit using our crafted mask
  INTERNAL_SIZE_T chunksize = p->size & mark;
  // Get the final data size of the chunk, the array length in bytes
  INTERNAL_SIZE_T arrayLength = chunksize - CHUNK_HDR_SZ;
  printf("arr pointer size %zu\n", arrayLength);
  arrlen(arrPrt);

  printf("arr pointer true size %zu\n", arrlen(arrPrt) / sizeof(short));
  arrayLenghtPoC(arrPrt);
  free(arrPrt);
  arrPrt = NULL;
  return 0;
}
