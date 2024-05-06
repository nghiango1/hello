# Import method/class directly
from datastructure.myStack import Stack
from datastructure.myQueue import Queue

# Import a file and use it as module
import datastructure.myQueue

# Import a file and use it as module using a different name to prevent conflict
import datastructure.myQueue as q

# Import a directory and use it as module (also using a different name to prevent conflict)
import datastructure
import datastructure as ds
import datastructure.__init__
from datastructure import Queue as Q


def main():
    print("Manunal stack test")
    stack = Stack()
    stack.add(3)
    print(stack.pop())

    print("Manunal queue test")
    for i in range(8):
        queue = None
        match i:
            case 0:
                # We access to Queue from `from datastructure.myQueue import Queue` import
                queue = Queue()
            case 1:
                # We access to Queue through `datastructure.myQueue` module
                queue = datastructure.myQueue.Queue()
            case 2:
                # We access to Queue through `datastructure.myQueue` module with different name `q`
                queue = q.Queue()
            case 3:
                # We access to Queue through `datastructure` module
                queue = datastructure.Queue()
            case 4:
                # We access to Queue through `datastructure.__init__` module, which is the same with case 3
                queue = datastructure.__init__.Queue()
            case 5:
                queue = Q()
            case 6:
                # As we already import datastructure.myQueue this is good
                queue = datastructure.myQueue.Queue()
                # This also fine, even when acessing datastructure through `ds`, which should only exposing Queue, not myQueue
                # As we already import datastructure.myQueue this is good
                try:
                    queue = ds.myQueue.Queue()
                except Exception as e:
                    print("myQueue isn't exposed from `ds` yet, got Error: ", e)
                # This will error, as datastructure.__init__ only exposing Queue, not myQueue
                try:
                    queue = datastructure.__init__.myQueue.Queue()
                except Exception as e:
                    print("myQueue isn't exposed from datastructure.__init__ yet, got Error: ", e)
            case _:
                # This is default case for match syntax
                queue = Queue()
        queue.add(1)
        queue.add(2)
        queue.add(3)
        print(queue.__str__())

if __name__ == "__main__":
    main()
