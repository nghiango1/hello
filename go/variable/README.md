# Variable

```
go mod init example/variable
```

## Init a variable

You start with `var`

```go
var message string
//          ^^^^^^--- Type
//  ^^^^^^^----- Name
```

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
