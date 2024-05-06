from typing import Generic, Optional, TypeVar

T = TypeVar("T")


class QNode(Generic[T]):
    """Single Linked list node structure for Queue implement

    Attributes:
        v: Node contained value
        next: Linked to the next QNode or None
    """

    def __init__(self, v: T, nextLink=None):  # noqa: F821
        self.v: T = v
        self.next: Optional[QNode[T]] = nextLink

    def __str__(self):
        return "{value}".format(value=str(self.v))


class QueueIterator(Generic[T]):
    """Iterator interface

    An iterator object implements __next__, which is expected to return the next element of the iterable object that returned it, and to raise a StopIteration exception when no more elements are available.
    """

    def __init__(self, queueHead: Optional[QNode[T]]):
        self.__current = queueHead

    def __iter__(self):
        """Iteratable interface for enumeration() support, pyright will error if this __iter__ method isn't implement"""
        return self

    def __next__(self) -> T:
        if self.__current is None:
            raise StopIteration

        res = self.__current.v
        self.__current = self.__current.next
        return res


class Queue(Generic[T]):
    """Queue implement using linked list"""

    def __init__(self):
        self.__head: Optional[QNode[T]] = None
        self.__tail: Optional[QNode[T]] = None
        self.__length: int = 0

    def add(self, v: T):
        """
        Add a new element into the queue

        Args:
            v: Elements to be added

        Raises:
            ValueError: This should not never happend
        """
        qNode: QNode[T] = QNode(v, None)
        if self.__head is None:
            self.__head = self.__tail = qNode
        else:
            # This should alway be true
            if self.__tail is not None:
                self.__tail.next = qNode
                self.__tail = self.__tail.next
            else:
                raise ValueError("What, tail is none when head already init?!?")
        self.__length += 1

    def pop(self) -> Optional[T]:
        """
        Get a element out of the queue, the first to get in will be the first to get out

        Returns:
        - Get the first element out of queue
        - None if there isn't any element in the queue left
        """
        res: Optional[T] = None
        if self.__head is not None:
            res = self.__head.v
            self.__head = self.__head.next

        if self.__head is None:
            self.__tail = None

        self.__length -= 1
        return res

    def isEmpty(self):
        """Check if a queue is empty - which mean there currently no element in queue

        Returns: Boolean (True/False) value representing the queue empty state
        """
        return self.__head is None

    def __iter__(self):
        """Iterable interface compliance

        Returns: QueueIterator object for interating the queue
        """
        return QueueIterator[T](self.__head)

    def __str__(self):
        # We using iteralbe here to quickly interate the queue
        reduceString = ", ".join(str(i) for i in self)

        return "[{string}]".format(string=reduceString)


def main():
    queue = Queue[int]()
    print("Test queue.add(), adding 4 new element [1,2,3,4]")
    queue.add(1)
    queue.add(2)
    queue.add(3)
    queue.add(4)

    print("Test queue.__str__()")
    print(queue)

    print("Test queue.__iter__()")
    for value in queue:
        print("{0}".format(value))

    for index, value in enumerate(queue):
        # Python format using {{, }} to escape {, }
        print("{{{0}: {1}}}".format(index, value))

    print("Test queue.pop()")
    for _ in range(5):
        t = queue.pop()
        if t is not None:
            print(t)
        else:
            print("Queue is empty, current queue = ", queue)


if __name__ == "__main__":
    main()
