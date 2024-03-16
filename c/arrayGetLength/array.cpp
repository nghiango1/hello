// provides `NULL`, `free`, `malloc`, `size_t`
#include <cstdlib>

// provides `printf`
#include <stdio.h>

// Special use case
typedef unsigned short INTERNAL_SIZE_T;
// typedef size_t INTERNAL_SIZE_T;

// Identical malloc.c implement
struct ChunkHeader {
  INTERNAL_SIZE_T prev_size; /* Size of previous chunk (if free).  */
  INTERNAL_SIZE_T size;      /* Size in bytes, including overhead. */

  struct malloc_chunk *fd; /* double links -- used only if free. */
  struct malloc_chunk *bk;

  /* Only used for large blocks: pointer to next larger size.  */
  struct malloc_chunk *fd_nextsize; /* double links -- used only if free. */
  struct malloc_chunk *bk_nextsize;
};

// Function to get the chunk header from a memory address as function
struct ChunkHeader *mem2chunk(void *mem, int chunk_hdr_sz) {
  if (mem == NULL) {
    return NULL;
  }
  return (ChunkHeader *)((char *)mem - chunk_hdr_sz);
}

// Print
int printHex(void *arr, int length) {
  printf("Array with %d length:\n", length);
  for (int i = 0; i < length; i++) {
    printf("%08x", ((unsigned int *)arr)[i]);
    if (i != length - 1)
      printf(", ");
  }
  printf("\n");

  return 0;
}

void printSize(short arr[]) {
  printf("size of arr (function decay) = %lu\n", sizeof(*arr));
  printf("size of arr (function decay) element = %lu\n", sizeof(arr[0]));
}

void freeArr(short arr[]) {
  free(arr);
  arr = NULL;
}

void getSize(short arr[], int chunk_hdr_sz) {
  ChunkHeader *p = mem2chunk(arr, chunk_hdr_sz);
  printf("(function decay) size %hu, prev_size %hu\n", p->size, p->prev_size);
}

int main() {
  short arr[4];
  // Stack alocated array
  printf("size of arr = %lu\n", sizeof(arr));
  printf("size of arr element = %lu\n", sizeof(arr[0]));
  printSize(arr);

  short *arrPrt;
  arrPrt = (short *)malloc(sizeof(short) * 16);
  for (int i = 0 ; i < 16; i++) {
    arrPrt[i] = i;
  }

  short *arrPrt2;
  arrPrt2 = (short *)malloc(sizeof(short) * (32+64));
  for (int i = 0 ; i < 64+32; i++) {
    arrPrt2[i] = i;
  }

  // This not work anyway, sizeof only work with stack alocated memory
  printf("size of arr (pointer) = %lu\n", sizeof(arrPrt));
  printf("size of arr (pointer) element = %lu\n", sizeof(arrPrt[0]));

  printSize(arrPrt);

  // Chunk header instead
  int chunk_hdr_sz = 10;
  ChunkHeader *p = mem2chunk(arrPrt, chunk_hdr_sz);
  printf("shift = %d, with size %hu, prev_size %hu\n", chunk_hdr_sz, p->size, p->prev_size);
  getSize(arrPrt, chunk_hdr_sz);

  freeArr(arrPrt);

  return 0;
}
