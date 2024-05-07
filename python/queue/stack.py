from typing import Generic, Optional, TypeVar

T = TypeVar("T")


class Node(Generic[T]):
    """
    Linked list node

    Attributes:
        v:
        next:
    """

    def __init__(self, v: T, nextLink=None):  # noqa: F821
        self.v: T = v
        self.next: Optional[Node] = nextLink


class StackIterator(Generic[T]):
    def __init__(self, node: Optional[Node[T]]):
        self.node = node

    def __iter__(self):
        return self

    def __next__(self):
        if self.node is not None:
            value = self.node.v
            self.node = self.node.next
            return value
        raise StopIteration


class Stack(Generic[T]):
    """Stack implement using Single linked-list"""

    def __init__(self):
        self.__top: Optional[Node[T]] = None
        self.__length: int = 0

    def getLength(self):
        """
        Get the length of stack - or total number of elements in the stack

        Returns: Int length value
        """
        return self.__length

    def add(self, v: T):
        """
        Adding a new element into the stack

        Args:
            v: The added element
        """
        if self.__top is None:
            self.__top = Node[T](v, None)
        else:
            self.__top = Node[T](v, self.__top)
        self.__length += 1

    def pop(self) -> Optional[T]:
        """
        Take out the top of stack element, following first in last out rule

        Returns:
        - The value of the last added element
        - None if the stack is empty
        """
        result = None

        if self.__top is not None:
            result = self.__top.v
            self.__top = self.__top.next

        self.__length -= 1

        return result

    def isEmpty(self):
        """
        Check if the stack is empty (no element in the stack)

        Returns: A boolean (True/False) value
        """
        return self.__top is None

    def __iter__(self):
        return StackIterator(self.__top)


if __name__ == "__main__":
    stack = Stack[str]()
    stack.add("1")
    stack.add("2")
    stack.add("3")
    stack.add("4")

    for i in stack:
        print(i)

    for index, value in enumerate(stack):
        print(index, value)

    for i in range(5):
        t = stack.pop()
        if t is not None:
            print(t)
        else:
            print("Stack is empty")
