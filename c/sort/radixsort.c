#include <time.h>
#include <stdlib.h>
#include <stdio.h>

void printBitArray(int* a, int length) {
    for (int i = 0; i < length; i ++) {
        printf("%08x, ", a[i]);
    }
    printf("\n");
}

void printArray(int* a, int length) {
    for (int i = 0; i < length; i ++) {
        printf("%d, ", a[i]);
    }
    printf("\n");
}

void radixsort(int* a, int l, int r, int bit) {
    if (l >= r) return;

    int i = l;
    int j = r;

    while (i <= j) {
        while (!((a[i] >> bit) & 1)) i ++;
        while ((a[j] >> bit) & 1) j --;
        
        if (i <= j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            i ++;
            j --;
        }
    }

    if (bit > 0) {
        radixsort(a, l, j, bit - 1);
        radixsort(a, i, r, bit - 1);
    }
}

int main() {
    int len = 10; 
    int a[1000];

    srand(time(NULL)); 
    for (int i = 0; i < len; i ++) {
        a[i] = rand(); 
    }

    a[0] = ~0;
    a[len - 1] = 0;
    printArray(a, len);
    printBitArray(a, len);
    radixsort(a, 0, len - 1, sizeof(a[0])*8-1);
    printArray(a, len);
    printBitArray(a, len);
}
