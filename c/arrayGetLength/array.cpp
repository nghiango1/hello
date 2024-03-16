// provides `ceil`
#include <math.h>

// provides `NULL`, `free`, `malloc`, `size_t`
#include <cstdlib>

// provides `printf`
#include <stdio.h>

// This base on gdb trace result, I'm unclear if it still this value in other
// chip architecture: ARM, MISP, ... or with a different glibc implement x64
#define CHUNK_HDR_SZ 16

// Print hex helper, so you don't need to use gdb to look at run time memory
// The function print in 4 bytes each, which will print longer than intended
// length if the length can't cover the last 4 bytes
int printHex(void *arr, int length) {
  int col = 1;
  // Length args need divide it by 4 to match with 4 bytes each print
  for (int i = 0; i < ceil(length/4.) ; i++) {
    printf("%08x, ", ((unsigned int *)arr)[i]);
    if (col % 4) {
      col += 1;
    } else {
      printf("\n");
      col = 1;
    }
  }
  printf("\n");

  return 0;
}

// Print sizeof value
void printSize(short arr[]) {
  printf("size of arr (function decay) = %lu\n", sizeof(*arr));
  printf("size of arr (function decay) element = %lu\n", sizeof(arr[0]));
}

// Check if we can free arr.
void freeArr(short arr[]) {
  free(arr);
  //arr = NULL;
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
  arrPrt2 = (short *)malloc(sizeof(short) * (32));
  for (int i = 0 ; i < 32; i++) {
    arrPrt2[i] = i;
  }

  // This not work anyway, sizeof only work with stack alocated memory
  printf("size of arr pointer = %lu\n", sizeof(arrPrt));
  printf("size of arr pointer element = %lu\n", sizeof(arrPrt[0]));
  // Not working with pointer too
  printSize(arrPrt);

  printf("\nHeap chunk in memory:\n");
  // Print the memory of the alocated chunk, here is the explain of lenght arg:
  // 16 (chunk_header) + 16*2 (alocated runtime array memory of arrPrt)
  // 16 (chunk_header) + 32*2 (alocated runtime array memory of arrPrt2)
  // total printed memory 16 + 32 + 16 + 64
  printHex((void *) ((char*)arrPrt - CHUNK_HDR_SZ), 16+32+16+64);

  printf("arrPrt Chunk header in memory:\n");
  printHex((void *) ((char*)arrPrt - CHUNK_HDR_SZ), 16);

  printf("arrPrt 2 chunk header in memory:\n");
  printHex((void *) ((char*)arrPrt2 - CHUNK_HDR_SZ), 16);

  // Check is we can free inside a function
  printf("(before free) %d != ", arrPrt[0]);
  freeArr(arrPrt);
  printf("%d (after free)\n", arrPrt[0]);
  freeArr(arrPrt2);
  return 0;
}
