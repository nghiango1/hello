#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct List {
  long *value;
  long length;
};

typedef struct List List;

void delete(List *a, long pos) {
  a->length = a->length - 1;
  for (long i = pos; i <= a->length; i++) {
    a->value[i] = a->value[i + 1];
  }
}

void push(List *a, long v) {
  a->length = a->length + 1;
  a->value[a->length] = v;
}

void init(List *a, long len) {
  a->value = (long *)malloc(sizeof(long) * len);
  a->length = 0;
}

void printList(List *a) {
  printf("List size %ld \n", a->length);
  // for (long i = 0; i < a->length; i++) printf(" %ld", a->value[i]);
  printf("\n");
}

int main() {
  List a;
  long multiple = 10000;
  long len = 100 * multiple;
  long i;
  long now;

  init(&a, len);
  srand(time(0));
  for (i = 0; i < len; i++) {
    push(&a, rand());
  }

  printList(&a);

  long total = 5 * multiple;
  long first = 50 * multiple;

  now = time(0);
  printf("start: %8ld \n", time(0));

  for (i = 0; i < total; i++) {
    long position = rand() % first;

    delete (&a, position);

    if (i % 1000 == 0)
      printf("%ld\n", i / 1000);
  }

  printf("End: %8ld \n", time(0) - now);

  printList(&a);
}
