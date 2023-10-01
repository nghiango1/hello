When using gorountines, you mind want to work with `sync.WaitGroup` for none return function

But, when a function returning value, a channels can be used for transfer child thread data

# Syntax

Sample code

```go
package main

import "fmt"

func sum(s []int, c chan int) {
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum // send sum to c
    //   ^^^ Push value to a channel, which need to have the same type with the channel
}

func main() {
	s := []int{7, 2, 8, -9, 4, 0}

	c := make(chan int)
//                 ^^^  Chanel type
//  ^^^^^^^^^^^^^^^^^^^ Chanel creation
	go sum(s[:len(s)/2], c)
	go sum(s[len(s)/2:], c)
	x, y := <-c, <-c // receive from c
//          ^^^  ^^^ This wait for a new value pushed to a channel can consumming them

	fmt.Println(x, y, x+y)
}
```

By the code above, we can see that:
- Using channels is another way to wait other gorountines thread to finish
- We can create multiple channels, with different type and purpose for better control

# Limiting gorountines concurrent operations with channel

```
