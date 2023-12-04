from typing import List


class QNode:
    def __init__(self, v, nextLink=None):
        self.v = v
        self.next: QNode = nextLink


class Queue:
    def __init__(self):
        self.head: QNode = None
        self.tail: QNode = None
        self.length = 0

    def add(self, v):
        qNode = QNode(v, None)
        if self.head is None:
            self.head = self.tail = qNode
        else:
            self.tail.next = qNode
            self.tail = self.tail.next
        self.length += 1

    def pop(self):
        res = None
        if self.head is not None:
            res = self.head.v
            self.head = self.head.next

        if self.head is None:
            self.tail = None

        self.length -= 1
        return res

    def isEmpty(self):
        return self.head is None


class Stack:
    def __init__(self):
        self.top = None
        self.length = 0

    def add(self, v):
        if self.top is None:
            self.top = QNode(v, None)
        else:
            self.top = QNode(v, self.top)
        self.length += 1

    def cycle(self):
        res = []
        p = self.top
        while p is not None:
            res.append(p.v.pop())
            prev = p
            p = p.next
            while p is not None and p.v.isEmpty():
                prev.next = p.next
                p = p.next
        return res

    def isEmpty(self):
        return self.top is None


class Solution:
    def findDiagonalOrder(self, nums: List[List[int]]) -> List[int]:
        queues = []
        for num in nums:
            internal = Queue()
            for v in num:
                internal.add(v)
            queues.append(internal)

        res = []
        stack = Stack()
        for q in queues:
            stack.add(q)
            res += stack.cycle()

        while not stack.isEmpty():
            res += stack.cycle()

        return res


if __name__ == "__main__":
    a = Solution()
    a.findDiagonalOrder([[1, 2, 3], [1], [3]])
