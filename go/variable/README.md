> Overal take, it a very generest programming language on function (int auto 64 bit, time also bigger, println accepting a lot more input, no need to free anything, ??variable in heap - not stack so it available in many scope??)

# Variable

```
go mod init example/variable
```

## Init a variable

You start with `var` to decleare a variable

```go
var message string = "Hello world!"
//          ^^^^^^--- Type
//  ^^^^^^^----- Name
```

Or in compact syntax with `:=`, which get type automatically

```go
message := "Hello world!"
```

> Also, we use `//` to start a comment (Unused code that ignore by the compiler)

This can either in the scope of function/main funtion or even outside
Start with this example file

```go
package main

import "fmt"

var message string

func main() {
     // Test
     message = "Hello world!"
     fmt.Println(message)
}
```

## Data type

Number integer: `int` default 64 bit
While working with memory, `unsafe` is the library we have to import, which make it seem like a danger zone to cross

```go
var i int 

func main() {
    fmt.Println(unsafe.Sizeof(i))
}
```

String: Above

## Structure type

### Linked list, Pointer

Start new type with `type`

```
## Check the variable.go code
```

- We can declare in main function, other function can access main function variable (??)

> This mean, index variable can be change inside a function, affecting the main function loop process
> Proccing a Bug (!!)

- We can decleare function after it being call (??)
- New object can be create using a normal `var`, or can be specifically create with pointer like this

    ```go
        var newNode *Node = &Node{v, nil};
    ```

- Pointer (Pretty much like C):
    - `*p` : Accessing true value
    - `&p` : Passing address
    - `var p *Node` : Decleare pointer

- For loop only (I can't even find While loop with cheat sheet `cht.sh`, it said go not have while loop)
    - `for i:=1; i < 10; i ++ {}`, remember to have `:=` for the index value, or you can accessing others parrent function 
    - `nil` check with `==`, same as c:
    ```go
        if config == nil {
            // then
        }
    ```
- Any class in go lang tranfer me go a **go way** of doing thing, [sample website](https://tip.golang.org/doc/effective_go).
    - Very little of OOP, this languagle isn't Java. 

### Array list, pointer(?)

There is no such thing here, a lot of `unsafe` code, slice can be a good replacement

```go
var slice []int = make(int, 0, 1_000_000)
//                             ^^^^^^^^^-Capacity
//                          ^------------Length
```

The `capacity` can be skip, slice can be auto extend if we just use `append`

```go
value := 10000 //any
append(slice, value)
```

Slice extending use some unsafe `memcopy` function, so it could be a `O(n)` cost of extending the array bellow it.

### Normal array list

Array have to init with a constant length, which isn't ideal (As I test on `1_000_000` size of List), it way more slower than expect (testing ArrayList vs LinkedList over 1_000_000 element)

```go
var array [1_000_000]int
```

## Uninitilize variable

Variables declared without an explicit initial value are given their zero value

The zero value is:

- 0 for numeric types,
- false for the boolean type, and
- "" (the empty string) for strings.

