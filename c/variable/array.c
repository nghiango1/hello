#include <stdio.h>
#include <string.h>

char hello[13] = "Hello world!";
long long arr[4] = {1, 1, 1, 1};
int arr_2[8] = {4, 4, 4, 4, 4, 4, 4, 4};

int printHex(unsigned int* arr, int length){
    printf("Array with %d length:\n", length);
    for (int i = 0; i < length; i++) {
        printf("%08x", ((unsigned int*)arr)[i]);
        if (i != length - 1) printf(", ");
    }
    printf("\n");

    return 0;
}

int main(){
    printf("size of arr = %lu\n", sizeof(arr));
    printf("size of arr element = %lu\n", sizeof(arr[0]));
    printHex((unsigned int*)arr, sizeof(arr)/sizeof(unsigned int));


    unsigned int *arr_view = (unsigned int *) &arr;
    unsigned int arr_length = (unsigned int) (sizeof(arr)/sizeof(unsigned int));
    printHex(arr_view, arr_length);

    arr_view[1] = (unsigned int)~0;
    printHex(arr_view, arr_length);
    printf("New value of arr[0] = %lld\n\n\n", arr[0]);


    printf("String: %s with length of %lu, memory size %lu\n", hello, strlen(hello), sizeof(hello) );
    for (int i = 0; i <= strlen(hello); i++) {
        printf("%2x ", hello[i]);
    }
    printf("\n");
    for (int i = 0; i <= strlen(hello); i++) {
        printf("%2c ", hello[i]);
    }
    printf("\n%s\n",hello);
    printHex((unsigned int*) hello, strlen(hello)/(4*sizeof(char)) + 1);
    printHex((unsigned int*) hello, 40);

    printf("\n");
    printf("\n");
    printf("\n");

    // The continue of the stack alocation
    printf("We working with arr_2 (all init with 4)\n");
    printHex((unsigned int*)arr_2, sizeof(arr_2)/sizeof(unsigned int));
    
    // Change arr, but arr_2 will be affected
    for (int i = 4; i < 8; i++) {
        arr[i] = 2;
    }

    printHex((unsigned int*)arr_2, sizeof(arr_2)/sizeof(unsigned int));

    return 0;
}
