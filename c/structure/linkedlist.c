#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct ListNode {
  long value;
  struct ListNode *next;
};

typedef struct ListNode ListNode;

struct List {
  ListNode *head, *tail;
  long length;
};

typedef struct List List;

void delete(List *a, long pos) {
  a->length = a->length - 1;
  ListNode *p, *tmp;

  if (pos == 1) {
    p = a->head;
    a->head = a->head->next;
    free(p);
    return;
  }

  p = a->head;
  long i = 0;
  while (p != 0) {
    i++;
    if (i == pos) {
      tmp = p->next;
      p->next = tmp->next;
      free(tmp);
      if (a->head == 0) {
        a->tail = 0;
      }
      return;
    } else {
      p = p->next;
    }
  }
}

void push(List *a, long v) {
  a->length = a->length + 1;
  ListNode *p, *tmp;

  if (a->head == 0) {
    p = malloc(sizeof(ListNode));
    a->head = p;
    a->tail = p;
    p->value = v;
    p->next = 0;
    return;
  }

  p = malloc(sizeof(ListNode));
  p->value = v;
  p->next = 0;
  a->tail->next = p;
  a->tail = a->tail->next;
}

void init(List *a) {
  a->head = 0;
  a->tail = 0;
  a->length = 0;
}

void printList(List *a) {
  ListNode *p = a->head;
  printf("List size %ld \n", a->length);
  // while (p != 0) {
  //   printf(" %ld", p->value);
  //   p = p->next;
  // }
  printf("\n");
}

int main() {
  List a;
  init(&a);

  long multiple = 10000;
  long len = 100 * multiple;
  long i;
  long now;

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
