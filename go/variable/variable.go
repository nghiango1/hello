package main

import (
	"fmt"
	"unsafe"
 	"math/rand"
    "time"
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

type List struct {
    head *Node
    tail *Node
    length int
}

func initList(l *List) {
    l.length = 0
    l.head = nil
    l.tail = nil
}

func push(l *List, v int) {
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

func printListCompact(l *List) {
    fmt.Printf("List length = %d", l.length)
    fmt.Println()
}

func printList(l *List) {
    var p *Node
    p = l.head

    fmt.Printf("List length = %d, with element:", l.length)
    for i:= 1; i < l.length; i ++ {
        fmt.Printf(" %d", p.value)
        if p != nil { p = p.next }
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

    var list List
    initList(&list)

    var multi int = 10_000
    var length int = 100*multi
    var total int = 5*multi
    var first int = 50*multi
    var i int

    for i = 0; i < length; i++ {
        push(&list, rand.Intn(1000000))
    }

    printListCompact(&list)

    now := time.Now()
    for i = 0; i < total; i++ {
        var pos int = rand.Intn(first)
        deleteList(&list, pos)
        if i % 1000 == 0 {
            fmt.Printf("%d ", i / 1000)
        }
    }

    fmt.Println()

    fmt.Println( (- now.UnixMilli() + time.Now().UnixMilli()) / 1000)
    printListCompact(&list)
}

func deleteList(l *List, pos int) {
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

