all: build

INCLUDE_DIR := ./include
BUILD_DIR := ./build
SRC_DIR := ./src

.PHONY: build
build:
	mkdir -p $(BUILD_DIR)
	g++ $(SRC_DIR)/main.cpp -c -o $(BUILD_DIR)/main.o -I$(INCLUDE_DIR)
	g++ $(SRC_DIR)/hello.cpp -c -o $(BUILD_DIR)/hello.o -I$(INCLUDE_DIR)
	g++ -o main $(BUILD_DIR)/main.o $(BUILD_DIR)/hello.o
	./main

.PHONY: share-lib
share-lib:
	mkdir -p $(BUILD_DIR)
	g++ $(SRC_DIR)/hello.cpp -shared -o libhello.so -I$(INCLUDE_DIR)
	g++ $(SRC_DIR)/main.cpp -o main -L. -lhello -I$(INCLUDE_DIR)
	env LD_LIBRARY_PATH=. ./main

.PHONY: static-lib
static-lib:
	mkdir -p $(BUILD_DIR)
	g++ $(SRC_DIR)/hello.cpp -c -o $(BUILD_DIR)/hello.o -I$(INCLUDE_DIR)
	ar r libhello.a $(BUILD_DIR)/hello.o
	g++ $(SRC_DIR)/main.cpp -o main -I$(INCLUDE_DIR) -lhello -L. --static
	./main

.PHONY: clean
clean:
	rm -f main
	rm -f libhello.so
	rm -r -f $(BUILD_DIR)
