package main

import (
	"example/module/list"
	"example/module/sample"
	"fmt"
	//              ^^^^- package name
	//^^^^^^^^^^^^^------ go mod init name
)

type point struct {
	x int
	y int
}

type point3d struct {
	x int
	y int
	z int
}

func (p *point) printPoint() {
	fmt.Printf("Point (%d, %d)\n", p.x, p.y)
}

func printPoint(p * point) {
	fmt.Printf("Point (%d, %d)\n", p.x, p.y)
}

func (p *point3d) printPoint() {
	fmt.Printf("Point (%d, %d, %d)\n", p.x, p.y, p.z)
}

func printHello() {
	fmt.Print("Hello")
}

func printString(s string) {
	fmt.Println(s)
}

func modify(s *string) {
	*s = "     Chage?!"
}

func add(a int, b int) int {
	return a + b
}

func main() {
	printHello()
	s := " World!"
	printString(s)
	modify(&s)
	printString(s)
	test.Exported(&s)
	printString(s)
	println(add(1, 3)) //not even need fmt??

    var p point
    p.x = 1
    p.y = 2
    p.printPoint()
    
	var al list.ArrayList
	al.Init()
	al.Push(1)
	al.Push(2)
	al.Print()

	var ll list.LinkedList
	ll.Init()
	ll.Push(1)
	ll.Push(2)
	ll.Print()
}
