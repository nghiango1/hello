"""`__init__.py` only expose Queue class from this directory file myQueue.py.
So when trying to:
- Import `datastructure` directly, we can't access Stack or myStack, myQueue, ...
- We can only access Queue directly through `datastructure`
"""

from .myQueue import Queue

