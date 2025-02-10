.PHONY: all build test
all: build

debug: CFLAG+= -g -O0
debug: build

build:
	@mkdir -p ./dist
	g++ ./main.cpp -o ./dist/main $(CFLAG)

test: build
	cat ./test.in | ./dist/main
