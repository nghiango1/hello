CC=/usr/bin/g++


all: build exec

build:
	$(CC) Main.cc -o Main -O2 -Wall -lm -static -std=gnu++17 -DONLINE_JUDGE -DBOJ

debug:
	$(CC) Main.cc -o Main -O0 -g -Wall -lm -static -std=gnu++17 -DONLINE_JUDGE -DBOJ

exec:
	./Main

clean:
	rm *.o ./Main
