from typing import List, Optional
import copy


class Helper:
    def __init__(self, n):
        self.arr = [-1] * (n * 2 - 1)
        self.used = set()

    def getFirst(self):
        for i in range(len(self.arr)):
            if self.arr[i] != -1:
                continue
            return i
        return -1

    def put(self, index, value):
        self.arr[index] = value
        if value != 1:
            self.arr[index + value] = value
        self.used.add(value)

    def remove(self, index, value):
        self.arr[index] = -1
        if value != 1:
            self.arr[index + value] = -1
        self.used.remove(value)

    def copy(self):
        return copy.deepcopy(self)

    def __str__(self):
        return "[" + ", ".join([str(i) for i in self.arr]) + "]"


class Solution:
    def constructDistancedSequence(self, n: int) -> List[int]:
        self.n = n
        zero = Helper(n)
        self.solution: Optional[Helper] = None
        self.recusive(zero)
        if self.solution is None:
            raise RuntimeError("No solution found")
        return self.solution.arr

    def recusive(self, state):
        print(state.__str__())
        if self.solution is not None:
            return

        first = state.getFirst()
        if first == -1:
            self.solution = state.copy()
            return
        for i in range(self.n, 0, -1):
            if i in state.used:
                continue
            if i != 1 and (first + i >= 2 * self.n - 1 or state.arr[first + i] != -1):
                continue
            state.put(first, i)
            self.recusive(state)
            if self.solution is not None:
                return
            state.remove(first, i)


if __name__ == "__main__":
    n = 2
    a = Solution()
    result = a.constructDistancedSequence(n)
    print(result)
