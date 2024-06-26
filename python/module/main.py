from enum import Enum

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
from datastructure import Queue as DsQueue
from datastructure import myQueue

# Import method/class directly from directory.__init__
from datastructure.__init__ import Queue as DsiQueue

try:
    from datastructure.__init__ import myQueue as dsiMyQueue

    print("Wait what, this is ok now?")
    queue = dsiMyQueue.Queue()
    queue.add(1)
    print("dsiQueue value = ", queue)
except Exception as e:
    print("[Error] Nope, this isn't work, got Error: ", e)


class ImportType(Enum):
    # Import method/class directly
    # from datastructure.myQueue import Queue
    CLASS_FROM_FILE_MODULE_DIRECT = 0

    # Import a file and use it as module
    # import datastructure.myQueue
    FILE_MODULE = 1

    # Import a file and use it as module using a different name to prevent conflict
    # import datastructure.myQueue as q
    ALTERNATIVE_NAME_FILE_MODULE = 2

    # Import a directory and use it as module (also using a different name to prevent conflict)
    # import datastructure
    DIRECTORY_MODULE = 3
    # import datastructure as ds
    ALTERNATIVE_NAME_DIRECTORY_MODULE = 4
    # import datastructure.__init__
    DIRECTORY_INIT_MODULE = 5
    # import datastructure.__init__ as dsi
    ALTERNATIVE_NAME_DIRECTORY_INIT_MODULE = 6

    # Import method/class directly from directory
    # from datastructure import Queue as dsQueue
    ALTERNATIVE_NAME_CLASS_FROM_DIRECTORY_MODULE_DIRECT = 7
    # from datastructure import myQueue
    MODULE_FROM_DIRECTORY_MODULE_DIRECT = 8

    # Import method/class directly from directory.__init__
    # from datastructure.__init__ import Queue as dsiQueue
    ALTERNATIVE_NAME_CLASS_FROM_DIRECTORY_INIT_MODULE_DIRECT = 9


def createQueue(case: ImportType) -> Queue:
    """Is this a queue factory"""
    queue: Queue | None = None
    match case:
        case ImportType.CLASS_FROM_FILE_MODULE_DIRECT:
            # We access to Queue from `from datastructure.myQueue import Queue` import
            queue = Queue()
        case ImportType.FILE_MODULE:
            # We access to Queue through `datastructure.myQueue` module
            queue = datastructure.myQueue.Queue()
        case ImportType.ALTERNATIVE_NAME_FILE_MODULE:
            # We access to Queue through `datastructure.myQueue` module with different name `q`
            queue = q.Queue()
        case ImportType.DIRECTORY_MODULE:
            # We access to Queue through `datastructure` module
            queue = datastructure.Queue()
        case ImportType.ALTERNATIVE_NAME_DIRECTORY_MODULE:
            # We access to Queue through `datastructure` module with different name `q`
            queue = ds.Queue()
        case ImportType.DIRECTORY_INIT_MODULE:
            # We access to Queue through `datastructure.__init__` module, which behave the same with
            # ImportType.DIRECTORY_MODULE
            queue = datastructure.__init__.Queue()
        case ImportType.ALTERNATIVE_NAME_DIRECTORY_INIT_MODULE:
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
        case ImportType.ALTERNATIVE_NAME_CLASS_FROM_DIRECTORY_MODULE_DIRECT:
            queue = DsQueue()
        case ImportType.MODULE_FROM_DIRECTORY_MODULE_DIRECT:
            queue = myQueue.Queue()
        case ImportType.ALTERNATIVE_NAME_CLASS_FROM_DIRECTORY_INIT_MODULE_DIRECT:
            queue = DsiQueue()
        case _:
            # This is default case for match syntax
            print("(Default)", end="")
            queue = Queue()

    return queue


def main():
    print("Manunal stack test")
    stack = Stack()
    stack.add(3)
    print(stack.pop())

    print("Manunal queue test")
    for i in ImportType:
        queue = createQueue(i)
        queue.add(1)
        queue.add(2)
        queue.add(3)
        print("Case {0}: Queue value = {1}".format(i, queue))


if __name__ == "__main__":
    main()
