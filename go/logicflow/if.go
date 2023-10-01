package main

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

func main() {
    test(1,10_000, 1)
    test(0,10, 1)
}
