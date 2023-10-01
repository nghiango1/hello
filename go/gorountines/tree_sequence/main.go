package main

import "golang.org/x/tour/tree"

//	type Tree struct {
//	   Left  *Tree
//	   Value int
//	   Right *Tree
//	}
//
// Walk walks the tree t sending all values
// from the tree to the channel ch.
func Walk(t *tree.Tree, ch chan int) {
	if t.Left != nil {
		Walk(t.Left, ch)
	}
	ch <- t.Value
	if t.Right != nil {
		Walk(t.Right, ch)
	}
}

// Same determines whether the trees
// t1 and t2 contain the same values.
func Same(t1, t2 *tree.Tree) bool {
	c1 := make(chan int)
	go Walk(t1, c1)
	c2 := make(chan int)
	go Walk(t2, c2)
	for i := 0; i < 10; i++ {
		if <-c1 != <-c2 {
			return false
		}
	}
	return true
}

func main() {
	tree1 := tree.New(1)
	tree2 := tree.New(1)
	tree3 := tree.New(2)
	println(Same(tree1, tree1))
	println(Same(tree1, tree2))
	println(Same(tree1, tree3))
}
