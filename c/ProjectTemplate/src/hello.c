#include <stdio.h>
#include <world.h>

int main() {
  printf("Hello");
  char *s = world();
  printf(" %s\n", s);
}
