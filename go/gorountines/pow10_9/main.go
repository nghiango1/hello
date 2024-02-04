package main

import (
	"example/gorountines/randstring"
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Solution struct {
	jumpTable []([26]int)
	maxJump   []int
	totaltime int
	mu        sync.Mutex
}

func NewSolution() *Solution {
	return &Solution{
		jumpTable: nil,
		maxJump:   nil,
		totaltime: 0,
	}
}

func (sol *Solution) preprocess(t string) {
	var jumpTable []([26]int)
	var maxJump []int
	jumpTable = make([]([26]int), len(t)+1)
	maxJump = make([]int, len(t)+1)
	for j := 0; j < 26; j++ {
		jumpTable[len(t)][j] = -1
	}
	maxJump[len(t)] = 0
	var fullJump int = 0

	for i := len(t) - 1; i >= 0; i-- {
		fullJump = fullJump | (1 << (t[i] - 'a'))
		if fullJump == (1<<26)-1 {
			maxJump[i] = maxJump[i+1] + 1
			fullJump = 0
			//println(maxJump[i], i, fullJump)
		} else {
			maxJump[i] = maxJump[i+1]
		}
		c := t[i]
		for j := 0; j < 26; j++ {
			jumpTable[i][j] = jumpTable[i+1][j]
		}
		jumpTable[i][c-'a'] = i
	}
	sol.mu.Lock()
	sol.maxJump = maxJump
	sol.jumpTable = jumpTable
	sol.mu.Unlock()
}

func (sol *Solution) isSubsequence(s, t string) bool {
	sol.mu.Lock()
	jumpTable := sol.jumpTable
	maxJump := sol.maxJump
	sol.mu.Unlock()
	if jumpTable == nil {
		println("Not hit cache")
		sol.preprocess(t)
		sol.mu.Lock()
		jumpTable = sol.jumpTable
		maxJump = sol.maxJump
		sol.mu.Unlock()
	}

	idx := 0
	for i := 0; i < len(s); i++ {
		if len(s) <= maxJump[idx] {
			return true
		}
		c := s[i] - 'a'
		if jumpTable[idx][c] != -1 {
			idx = jumpTable[idx][c]
		} else {
			return false
		}
	}

	return true
}

func printTime(s string, totaltime time.Duration) {
	fmt.Print(s)
	fmt.Printf(" %s\n", totaltime)
}

const letterBytes = "abcdefghijklmnopqrstuvwxyz"

func (sol *Solution) test(id int, t string, s string, c chan time.Duration) {
	start := time.Now()
	sol.isSubsequence(s, t)
	c <- time.Now().Sub(start)
	if id%1_000_000 == 0 {
		println("Test", id, "completed")
	}
}

func main() {
	tlength := rand.Intn(10000)
	k := rand.Intn(1_000_000)
	t := randstring.RandString(tlength, []byte(letterBytes))

	sol := NewSolution()

	totaltime := time.Duration(0)
	testGenerationTime := time.Duration(0)

	c := make(chan time.Duration)
	println("T length = ", len(t), "with 100 first char =", t[:100])
	sol.preprocess(t)
	start := time.Now()
	for test := 0; test < k; test++ {
		start = time.Now()
		slength := rand.Intn(100)
		s := randstring.RandStringBytes(slength, []byte(letterBytes))
		testGenerationTime += time.Now().Sub(start)

		go sol.test(test, t, s, c)
		if test%1_000_000 == 0 {
			fmt.Print(test, " ")
		}
	}
	for test := 0; test < k; test++ {
		totaltime += <-c
	}
	printTime("ArrayList test done in:", totaltime)
	printTime("Total generation time:", testGenerationTime)
}
