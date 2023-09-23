package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
	"unsafe"
)

type Solution struct {
	jumpTable []([26]int)
	maxJump []int
	mu    sync.Mutex
}

func NewSolution() *Solution {
	return &Solution{
		jumpTable: nil,
		maxJump: nil,
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
    var fullJump int = 0;

    for i := len(t) - 1; i >= 0; i-- {
        fullJump = fullJump | (1 << (t[i] - 'a'))
        if fullJump == (1 << 26) - 1 {
            maxJump[i] = maxJump[i+1] + 1
            fullJump = 0
            println(maxJump[i], i, fullJump)
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

func printTime(totaltime time.Duration) {
	fmt.Printf("ArrayList test done in: %s\n", totaltime)
}

const (
	letterIdxBits = 6                    // 6 bits to represent a letter index
	letterIdxMask = 1<<letterIdxBits - 1 // All 1-bits, as many as letterIdxBits
	letterIdxMax  = 63 / letterIdxBits   // # of letter indices fitting in 63 bits
)

var src = rand.NewSource(time.Now().UnixNano())

const letterBytes = "abcdefghijklmnopqrstuvwxyz"

func RandString(n int, letterBytes []byte) string {
    b := make([]byte, n)
    // A src.Int63() generates 63 random bits, enough for letterIdxMax characters!
    for i, cache, remain := n-1, src.Int63(), letterIdxMax; i >= 0; {
        if remain == 0 {
            cache, remain = src.Int63(), letterIdxMax
        }
        if idx := int(cache & letterIdxMask); idx < len(letterBytes) {
            b[i] = letterBytes[idx]
            i--
        }
        cache >>= letterIdxBits
        remain--
    }

    return *(*string)(unsafe.Pointer(&b))
}

func main() {
	tlength := rand.Intn(10000)
	k := rand.Intn(100_000_000)
	t := RandString(tlength, []byte(letterBytes))

	sol := NewSolution()

	totaltime := time.Duration(0)
	printTime(totaltime)

    println("T length = ", len(t), "with 100 first char =", t[:100])
	for test := 0; test < k; test++ {
		slength := rand.Intn(100)
		s := RandString(slength, []byte(letterBytes))
		start := time.Now()
		sol.isSubsequence(s, t)
		totaltime += time.Now().Sub(start)
		if test%1_000_000 == 0 {
			fmt.Print(test, " ")
			printTime(totaltime)
		}
	}
}
