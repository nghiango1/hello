from main import *

run_cases = [
    (1, 200, 300),
    (2, 50, 250),
]

summit_case = run_cases + [
    (0, 0, 0),
    (0, 200, 200),
    (176, 350, 17950),
    (250, 100, 25100),
]


def test(input1, input2, expected_output):
    print("--------------------------------------")
    result = total_xp(input1, input2)
    if result == expected_output:
        print("Pass")
        return True
    print("False")
    return False


def main():
    for test_case in test_cases:
        test(*test_case)


test_cases = summit_case

main()
