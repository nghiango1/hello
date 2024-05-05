from typing import Generic, Optional, TypeVar

T = TypeVar("T")


class QNode(Generic[T]):
    def __init__(self, v: T, nextLink=None):  # noqa: F821
        self.v: T = v
        self.next: Optional[QNode] = nextLink


class Queue(Generic[T]):
    def __init__(self):
        self.head: Optional[QNode[T]] = None
        self.tail: Optional[QNode[T]] = None
        self.length: int = 0

    def add(self, v: T):
        """
        Add a new element into the queue

        Args:
            v: Elements to be added

        Raises:
            ValueError: This should not never happend
        """
        qNode: QNode[T] = QNode(v, None)
        if self.head is None:
            self.head = self.tail = qNode
        else:
            # This should alway be true
            if self.tail is not None:
                self.tail.next = qNode
                self.tail = self.tail.next
            else:
                raise ValueError("What, tail is none when head already init?!?")
        self.length += 1

    def pop(self) -> Optional[T]:
        """
        Get a element out of the queue, the first to get in will be the first to get out

        Returns:
        - Get the first element out of queue
        - None if there isn't any element in the queue left
        """
        res: Optional[T] = None
        if self.head is not None:
            res = self.head.v
            self.head = self.head.next

        if self.head is None:
            self.tail = None

        self.length -= 1
        return res

    def isEmpty(self):
        return self.head is None


if __name__ == "__main__":
    queue = Queue[int]()
    queue.add(1)
    queue.add(2)
    queue.add(3)
    queue.add(4)

    for i in range(5):
        t = queue.pop()
        if t is not None:
            print(t)
        else:
            print("Queue is empty")
