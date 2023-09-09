# In file module

## Function/Procerude
Start with `func`

```
func main() {
    pass
}
```

## Struct bind function

This is some level of OOP support, this not `class`

```go
type point struct {
	x int
	y int
}

type point3d struct {
	x int
	y int
	z int
}

func (p *point) printPoint() {
	fmt.Printf("Point (%d, %d)\n", p.x, p.y)
}

func printPoint(p * point) {
	fmt.Printf("Point (%d, %d)\n", p.x, p.y)
}

func (p *point3d) printPoint() {
	fmt.Printf("Point (%d, %d, %d)\n", p.x, p.y, p.z)
}
```

By this way of declearing, we can use the same function name

> We can't create new method for other imported `type`

# Module file/folder structure

## Create submodule for import

Start with decleare package, we have a example a folder `sample` only have only one package `test` define, this rule is enforce

> All file in a folder have the same and only package name

To export function from a package, we need name the function with *F*irst letter as capital

```go
package test
//      ^^^^^--------------- This is name

func Exported(s *string) {
//   ^---------------------- This should be capital
	for {
		if (*s)[0] != ' ' {
			break
		}
		*s = (*s)[1:]
	}
}

func notExported() {}
```

If you want to use the package

```go
import {
    "example/module/sample"
//                  ^^^^^^-- This should folder name the same with pacakge name
//   ^^^^^^^^^^^^^^--------- This is name of module `go mod init <module name>`
}


main {
    test.Exported()
//  ^^^^-------------------- Importing sample folder mean import test package
}
```
