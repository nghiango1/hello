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
import datastructure.__init__ as dsi

# Import method/class directly from directory
from datastructure import Queue as dsQueue
from datastructure import myQueue

# Import method/class directly from directory.__init__
from datastructure.__init__ import Queue as dsiQueue

try:
    from datastructure.__init__ import myQueue as dsiMyQueue

    print("Wait what, this is ok now?")
    queue = dsiMyQueue.Queue()
    queue.add(1)
    print("dsiQueue value = ", queue)
except Exception as e:
    print("[Error] Nope, this isn't work, got Error: ", e)


def createQueue(case: int) -> Queue:
    """Is this a queue factory"""
    queue: Queue | None = None
    match case:
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
            # For more advange infomation, check `directoryImport.py`
            queue = datastructure.myQueue.Queue()
            try:
                # This will error, as `datastructure.__init__` only exposing Queue, not myQueue
                queue = datastructure.__init__.myQueue.Queue()
            except Exception as e:
                print(
                    "[Error] myQueue isn't exposed from datastructure.__init__ yet, got Error: ",
                    e,
                )
            try:
                queue = dsi.myQueue.Queue()
            except Exception as e:
                # This will error, with the same reason
                print("[Error] myQueue isn't exposed from `dsi`, got Error: ", e)
        case 6:
            queue = dsQueue()
        case 7:
            queue = myQueue.Queue()
        case 8:
            queue = dsiQueue()
        case _:
            # This is default case for match syntax
            queue = Queue()

    return queue


def main():
    print("Manunal stack test")
    stack = Stack()
    stack.add(3)
    print(stack.pop())

    print("Manunal queue test")
    for i in range(10):
        queue = createQueue(i)
        queue.add(1)
        queue.add(2)
        queue.add(3)
        print("Case {0}: Queue value = {1}".format(i, queue))


if __name__ == "__main__":
    main()
