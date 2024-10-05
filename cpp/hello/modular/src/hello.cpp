#include "hello.h"

void hello(std::string name) { printf("Hello %s!\n", name.c_str()); }
void hello(std::string *name) { printf("Hello (p) %s!\n", name->c_str()); }
