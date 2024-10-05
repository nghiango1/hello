#include "hello.h"

int main(int args, char **agrv) {
  // one name
  hello("world");

  // multiple arg type support
  std::string world{"world"};
  hello(&world);

  return 0;
}
