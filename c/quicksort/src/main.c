#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void swap(int *x, int *y) {
  int tmp = *x;
  *x = *y;
  *y = tmp;
}

int partition(int *nums, int low, int high) {
  int pivot = nums[high];
  int i = (low - 1);

  for (int j = low; j <= high - 1; j++) {
    if (nums[j] <= pivot) {
      i++;
      swap(&nums[i], &nums[j]);
    }
  }
  swap(&nums[i + 1], &nums[high]);
  return (i + 1);
}

void quicksort(int *nums, int low, int high) {
  if (low < high) {
    int pivotIndex = partition(nums, low, high);
    quicksort(nums, low, pivotIndex - 1);
    quicksort(nums, pivotIndex + 1, high);
  }
}

int test(int *nums, int numsSize, int k) {
  printf("k = %d\n", k);
  for (int i = 0; i < numsSize; i++) {
    printf("%d ", nums[i]);
  }
  printf("\n");
  quicksort(nums, 0, numsSize - 1);
  for (int i = 0; i < numsSize; i++) {
    printf("%d ", nums[i]);
  }
  return 0;
}

int main() {
  srand(time(NULL));
  int size = 5;
  int *numbers = (int *)malloc(size * sizeof(int));

  for (int i = 0; i < size; i++) {
    numbers[i] = 2;
  }

  numbers[0] = 12;
  numbers[3] = 12;

  test(numbers, size, 3);
  return 0;
}
