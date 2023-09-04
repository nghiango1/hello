from math import trunc, log


def binarySearch(arr, x):
    l, r = -1, len(arr)
    n = len(arr)
    logn = trunc(log(n+2,2))+1
    for _ in range(logn):
        m = (l + r) // 2
        if l == r-1:
            break
        if x < arr[m]:
            r = m
        else:
            l = m
    assert l == r-1
    return x == arr[l], l



arr = [1, 2, 2, 2, 2, 3, 4, 6, 8, 9, 142, 142, 142, 142, 255, 255, 255, 567, 1275, 1275, 1275, 2547, 2547, 5458, 9722, 92124]
x = 567

isFound, lastPossition = binarySearch(arr, x)
