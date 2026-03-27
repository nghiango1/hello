#include <time.h>
#include <stdlib.h>
#include <stdio.h>

// Lmao
void swap(int *x, int *y) {
    // 2 address point to the same number
    if (x == y)
        return;
    // printf("Start : %d %d\n", *x, *y);
    *x ^= *y; // x ^ y , y
    // printf("Step 1: %d %d\n", *x, *y);
    *y ^= *x; // x ^ y , x
    // printf("Step 2: %d %d\n", *x, *y);
    *x ^= *y; // y , x
    // printf("Final : %d %d\n", *x, *y);
}

void reverse(int* a, int len) {
    for (int i = 0; i < len; i++) {
        if (i >= len / 2) {
            break;
        }
        swap(&a[i], &a[len-1-i]);
    }
}

void printArray(int* a, int length) {
    for (int i = 0; i < length; i ++) {
        printf("%d, ", a[i]);
    }
    printf("\n");
}

#define LENGTH 11

int main() {
    int len = LENGTH; 
    int a[LENGTH];

    srand(time(NULL)); 
    for (int i = 0; i < len; i ++) {
        a[i] = rand(); 
    }

    printf("[INFO] Before: ");
    printArray(a, len);
    reverse(a, len);
    printf("[INFO] After : ");
    printArray(a, len);
}
