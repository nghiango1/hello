all: build test

DEBUG:= -g -O0 -DDEBUG

debug: DEBUG:= -g -O0 -DDEBUG
debug: all

build:
	g++ main.cpp -o ./dist/main --std=c++17 $(DEBUG)

test:
	cat ./test.in | ./dist/main
