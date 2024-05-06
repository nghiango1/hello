"""
# Public module
`__init__.py` only expose Queue class from this directory file myQueue.py.

So when trying to import this with:
```python
import datastructure
# (or) import datastructure.__init__
# (or) import datastructure as ds
```
we can access both `myQueue` (module) and `Queue` (class) through `datastructure` module
```python
q = datastructure.myQueue.QNode
```

or can access only `Queue` (class) through `datastructure.__init__` module
```python
q = datastructure.__init__.QNode
```

So when we doing something like this, which only accessing through `dsi`module
```python
import datastructure.__init__ as dsi
```
We can force the code to only able to access `Queue` (class)

# Private module

We also expose Queue class from this directory file __myPrivateQueue.py, chaning it name to PrivateQueue so there isn't any conlict. As the file have prefix `__`, it will be hidden and atc as a private module which "can't be" directly access from `datastructure` regardless of way we importing it

Or TLDR We can only access PrivateQueue directly through `datastructure` and hide __myPrivateQueue module regardless of how we import the module

*The double qoute is there because this is just syntax suggar, which isn't do anything. The python code still run fine even when LSP show error*
"""

from .myQueue import Queue

from .__myPrivateQueue import Queue as PrivateQueue

