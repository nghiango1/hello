package list

import (
	"fmt"
)

type ArrayList struct {
	value  []int32
	length int
}

func (l *ArrayList) Init() {
    l.value = make([]int32, 0)
	l.length = 0
}

func (l *ArrayList) Push(v int32) {
    l.value = append(l.value, v)
	l.length += 1
}

func (l *ArrayList) PrintCompact() {
	fmt.Printf("List length = %d", l.length)
	fmt.Println()
}

func (l *ArrayList) Print() {
	fmt.Printf("List length = %d, with element:", l.length)
	for i := 0; i < l.length; i++ {
		fmt.Printf(" %d", l.value[i])
	}
	fmt.Println()
}

func (l *ArrayList) Delete(pos int) {
	l.length--

    for i := pos; i < l.length; i++ {
		l.value[i] = l.value[i+1]
	}
}
