package list

import "fmt"

type Node struct {
	value int
	next  *Node
}

type LinkedList struct {
	head   *Node
	tail   *Node
	length int
}

func (l *LinkedList) Init() {
	l.length = 0
	l.head = nil
	l.tail = nil
}

func (l *LinkedList) Push(v int) {
	l.length += 1
	var newNode *Node = &Node{v, nil}
	if l.head == nil {
		l.head = newNode
		l.tail = l.head
	} else {
		l.tail.next = newNode
		l.tail = l.tail.next
	}
}

func (l *LinkedList) PrintCompact() {
	fmt.Printf("List length = %d", l.length)
	fmt.Println()
}

func (l *LinkedList) Print() {
	var p *Node
	p = l.head

	fmt.Printf("List length = %d, with element:", l.length)
	for i := 0; i < l.length; i++ {
		fmt.Printf(" %d", p.value)
		if p != nil {
			p = p.next
		}
	}
	fmt.Println()
}

func (l *LinkedList) Delete(pos int) {
	var p *Node
	var i int

	l.length--
	p = l.head
	if pos == 0 {
		l.head = l.head.next
		if l.head == nil {
			l.tail = nil
		}
		return
	}

	for i = 1; i < pos; i++ {
		p = p.next
	}

	var nextP *Node = p.next
	p.next = nextP.next
}
