from math import trunc, log
from typing import List


def binarySearch(arr: List[int], target: int):
    """

    Args:
        arr: Array of interger that need to search thorugh for the target, it should already be sort in increasing order
        target: A interger value needed to be searched

    Returns:
        tuple of (isFound, lastPossition)
    """
    left, right = -1, len(arr)
    length = len(arr)
    log2n = trunc(log(length + 2, 2)) + 1
    for _ in range(log2n):
        mid = (left + right) // 2
        if left == right - 1:
            break
        if target < arr[mid]:
            right = mid
        else:
            left = mid
    assert left == right - 1
    return target == arr[left], left


def main():
    arr = [
        1,
        2,
        2,
        2,
        2,
        3,
        4,
        6,
        8,
        9,
        142,
        142,
        142,
        142,
        255,
        255,
        255,
        567,
        1275,
        1275,
        1275,
        2547,
        2547,
        5458,
        9722,
        92124,
    ]
    x = 567

    isFound, lastPossition = binarySearch(arr, x)
    print(isFound, lastPossition)


if __name__ == "__main__":
    main()
