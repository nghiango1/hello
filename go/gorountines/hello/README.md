# Sample Hello world with rountines

A goroutine is a lightweight thread managed by the Go runtime.

```go
go f(x, y, z)
```

starts a new goroutine running

# Hello

```go
func say(s string) {
    for i := 0; i < 5; i++ {
    time.Sleep(100 * time.Millisecond)
        fmt.Println(s)
    }
}

func main() {
    go say("world")
    say("hello")
}
```

This example have say "world" in a diferrent rountines (thread)

# Main process suddend end

## Problem

> While this is true that `say("world")` is different from `say("hello")`, not calling `say("hello")` or using go `say("hello")` result a null output (there is not thing on stdout)

```go
func main() {
    go say("world")
    go say("hello")
}
```

> This mean,  effectively we have to call `say` again for this to even work.

## Why it happen

This is because go can already end the main program and not waiting for any other thread created to finish, making all child process end before even output any string

To wait for other gorountine to finish, we can use `wg` or `sinc.WaitGroup` helper
- Create one `wg` instance
- `wg.Add(1)` and `defer wg.Done()` before each gorountine function call (this here we have a lot of ways, but this kept it simple enough)
- `wg.Wait()` in the main program

```go
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
```
