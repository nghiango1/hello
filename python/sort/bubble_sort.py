def bubbleSort(arr):
    for i in range(len(arr)):
        for j in range(len(arr) - i-1):
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]


arr = [124, 1241, 412, 4, 54, 5, 34, 12, 4, 12, 321, 3, 33]
bubbleSort(arr)
print(arr)
