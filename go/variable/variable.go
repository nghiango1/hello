package main

import (
	"fmt"
	"math/rand"
	"time"
	"unsafe"
)

var message string
var i int // This default to be 8 bytes
var i2 int8
var i3 int16
var i4 int32
var i5 int64

type Node struct {
    value int
    next *Node
}

type LinkedList struct {
    head *Node
    tail *Node
    length int
}

func initLinkedList(l *LinkedList) {
    l.length = 0
    l.head = nil
    l.tail = nil
}

func pushLinkedList(l *LinkedList, v int) {
    l.length += 1;
    var newNode *Node = &Node{v, nil};
    if l.head == nil {
        l.head = newNode;
        l.tail = l.head;
    } else {
        l.tail.next = newNode;
        l.tail = l.tail.next;
    };
}

func printLinkedListCompact(l *LinkedList) {
    fmt.Printf("List length = %d", l.length)
    fmt.Println()
}

func printLinkedList(l *LinkedList) {
    var p *Node
    p = l.head

    fmt.Printf("List length = %d, with element:", l.length)
    for i:= 0; i < l.length; i ++ {
        fmt.Printf(" %d", p.value)
        if p != nil { p = p.next }
    }
    fmt.Println()
}

type ArrayList struct {
    value [1_000_000]int32
    length int
}

func initArrayList(l *ArrayList) {
    l.length = 0
}

func pushArrayList(l *ArrayList, v int32) {
    l.value[l.length] = v
    l.length += 1;
}

func printArrayListCompact(l *ArrayList) {
    fmt.Printf("List length = %d", l.length)
    fmt.Println()
}

func printArrayList(l *ArrayList) {
    fmt.Printf("List length = %d, with element:", l.length)
    for i:= 0; i < l.length; i ++ {
        fmt.Printf(" %d", l.value[i])
    }
    fmt.Println()
}

func main() {
    // Test commending
    message = "Hello world!"
    fmt.Println(message)
    fmt.Println(unsafe.Sizeof(i))
    fmt.Println(unsafe.Sizeof(i2))
    fmt.Println(unsafe.Sizeof(i3))
    fmt.Println(unsafe.Sizeof(i4))
    fmt.Println(unsafe.Sizeof(i5))

    var multi int = 10_000
    var length int = 100*multi
    var total int = 5*multi
    var first int = 50*multi

    var list LinkedList
    initLinkedList(&list)
    testLinkedList(length, list, total, first)

	var list2 ArrayList
    initArrayList(&list2)
    testArrayList(length, list2, total, first)
}

func testArrayList(length int, list2 ArrayList, total int, first int) {
	for i := 0; i < length; i++ {
		pushArrayList(&list2, int32( rand.Intn(1000000)))
	}

	printArrayListCompact(&list2)

	var now = time.Now()
	for i := 0; i < total; i++ {
		var pos int = rand.Intn(first)
		deleteArrayList(&list2, pos)
		if i%1000 == 0 {
			fmt.Printf("%d ", i/1000)
		}
	}

	fmt.Println()

	fmt.Println("ArrayList test done in: ", (-now.UnixMilli()+time.Now().UnixMilli())/1000, "s")
	printArrayListCompact(&list2)
}

func deleteArrayList(l *ArrayList, pos int) {
    l.length --

    for i = pos; i < l.length; i ++ {
        l.value[i] = l.value[i+1]
    }
}

func testLinkedList(length int, list LinkedList, total int, first int) {
	for i := 0; i < length; i++ {
		pushLinkedList(&list, rand.Intn(1000000))
	}

	printLinkedListCompact(&list)

	var now = time.Now()
	for i := 0; i < total; i++ {
		var pos int = rand.Intn(first)
		deleteLinkedList(&list, pos)
		if i%1000 == 0 {
			fmt.Printf("%d ", i/1000)
		}
	}

	fmt.Println()
	fmt.Println("LinkedList test done in: ", (-now.UnixMilli()+time.Now().UnixMilli())/1000, "s")
}

func deleteLinkedList(l *LinkedList, pos int) {
    var p *Node
    var i int

    l.length --
    p = l.head
    if pos == 0 {
        l.head = l.head.next
        if l.head == nil {
            l.tail = nil
        }
        return
    }

    for i = 1; i < pos; i ++ {
        p = p.next
    }

    var nextP *Node = p.next
    p.next = nextP.next
}
