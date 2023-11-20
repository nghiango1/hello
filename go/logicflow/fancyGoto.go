package main

import "fmt"

func testValidate(a, b, c bool) bool {
	if !a {
		println("a not true")
		return false
	}
	if !b {
		println("b not true")
		return false
	}
	if !c {
		println("c not true")
		return false
	}

	println("Validation pass")

	return true
}

func test(userID, transactinonTotal, authorizedFlag int) int {
	isUser := userID > 0
	isAdmin := userID == 0
	isAboveTransactinonLimit := transactinonTotal >= 10_000
	isAuthorized := authorizedFlag == 1

	if isAdmin {
		testValidate(isAdmin, true, isAuthorized)
	} else {
		testValidate(isUser, isAboveTransactinonLimit, isAuthorized)
	}

	return userID + transactinonTotal + authorizedFlag
}

func whileLoop() {
	println("While loop start")
	println("================")
	a := []int{1, 2, 3, 4, 5}
	index := 0
	for true {
		if index >= len(a) {
			break
		}
		println(a[index])
		index += 1
	}
}

func forLoop() {
	println("For loop start")
	println("================")
	a := []int{1, 2, 3, 4, 5}
	for i := 0; i < len(a); i++ {
		fmt.Printf("Index %d with value %d\n", i, a[i])
	}
}

func forEach() {
	println("For each loop start")
	println("================")
	a := []int{1, 2, 3, 4, 5}
	for i, v := range a {
		fmt.Printf("Index %d with value %d\n", i, v)
	}
}

func main() {
	test(1, 10_000, 1)
	test(0, 10, 1)
	whileLoop()
	forLoop()
	forEach()
}
