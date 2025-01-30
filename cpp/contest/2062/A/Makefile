.PHONY: all build test
all: build

CFLAG += -std=c++17

extra: CFLAG+= -Wall -Wextra -Wshadow -D_GLIBCXX_ASSERTIONS -DDEBUG -ggdb3 -fmax-errors=2
extra: build

debug: CFLAG+= -g -O0
debug: build

online: CFLAG += -DONLINE_JUDGE
online: build

build:
	@mkdir -p ./dist
	g++ ./main.cpp -o ./dist/main $(CFLAG)

test: build
	cat ./test/1.in | ./dist/main
