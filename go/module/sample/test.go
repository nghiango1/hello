package test

func exportedOrNode(s *string) {
	for {
		if (*s)[len(*s)-1] != ' ' {
			break
		}
		(*s) = (*s)[:len(*s)-1]
	}
}

func Exported(s *string) {
	for {
		if (*s)[0] != ' ' {
			break
		}
		*s = (*s)[1:]
	}
}

