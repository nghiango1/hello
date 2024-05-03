package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

func processingFile(f *os.File) {
	scanner := bufio.NewScanner(f)
	scanner.Split(bufio.ScanWords)

	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		fmt.Println(err.Error())
	}
}

func createDefaultFile(fileName string, content string) {
	f, err := os.Create(fileName)
	if err != nil {
		log.Fatal(err)
	}

	defer f.Close()

	_, werr := f.WriteString(content)
	if werr != nil {
		log.Fatal(werr)
	}
}

func main() {
	inputFileName := "inp.txt"
	f, err := os.Open(inputFileName)
	if err != nil {
		fmt.Println(err.Error())
		fmt.Println("Try create new file")
		createDefaultFile(inputFileName, "Hello world!")
	}
	processingFile(f)
}
