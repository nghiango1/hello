#include <time.h>
#include <stdlib.h>
#include <stdio.h>

void quicksort(int* a, int l, int r) {
    int i = l;
    int j = r;
    if (l > r) return;

    int target = a[(l + r) / 2];
    while (i <= j) {
        while (a[i] < target) i ++;
        while (a[j] > target) j --;
        if (i <= j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            i ++;
            j --;
        }
    }

    quicksort(a, l, j);
    quicksort(a, i, r);
}

void printArray(int* a, int length) {
    for (int i = 0; i < length; i ++) {
        printf("%d, ", a[i]);
    }
    printf("\n");
}

int main() {
    int len = 1000; 
    int a[1000];

    srand(time(NULL)); 
    for (int i = 0; i < len; i ++) {
        a[i] = rand(); 
    }

    quicksort(a, 0, len-1);
    printArray(a, len);
}
