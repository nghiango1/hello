package main

import (
	"fmt"
	"sync"
	"time"
)

func say(s string, t time.Duration, wg *sync.WaitGroup) {
    defer wg.Done()

	for i := 0; i < 5; i++ {
		time.Sleep(t * time.Millisecond)
		fmt.Println(s, i)
	}
}

func main() {
	var wg sync.WaitGroup

    wg.Add(1)
	go say("world", time.Duration(100), &wg)
    wg.Add(1)
	go say("hello", time.Duration(150), &wg)

    wg.Wait()
	println("Fin!")
}
