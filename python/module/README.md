# Module

Python support for module (import, code file split, etc...)

## Project structure

Print `tree` of the project

```sh
$ tree
.
├── datastructure
│   ├── __init__.py
│   ├── __myPrivateQueue.py
│   ├── myQueue.py
│   ├── myStack.py
└── main.py
```

## Python import - using other file method and class

There is a lot of way, here is a list with example

### 1. Import wanted function directly

```python
import datastructure.myQueue
from datastructure.myQueue import Queue
# import <FolderPath>.<File>.<Class>

# Now we can use Queue that have been decleare from datastructure/myQueue.py
q1 = Queue()
```

In `python` a `<File>` is call a `<Module>`

### 2. Import a file Module and access their method/class through it

Syntax

```python
import datastructure.myQueue
# import <FolderPath>.<File>.<Class>

q2 = datastructure.myQueue.Queue()
```

We can also have alternative name for shortance the accessing and preventing naming conflict
```python
import datastructure.myQueue as q
# import <FolderPath>.<File>.<Class> as <alternative name>

q2 = q.Queue()
```

### 3. Import a folder `datastructure/` directly as a Module

This require `__init__.py`, as the `import` will try to access that file if we try to import a directory. This will be almost the same as implementing __init__.py directly as a module

Syntax

```python
import datastructure
# import <FolderPath>
```

or

```python
import datastructure.__init__
```

We say almost, because `import datastructure` will expose the immediate module direclty even when we only want to exposing specific class through bellow code (We only import Queue here).
```python
from .myQueue import Queue
```

This can be "overcome" via naming module file with `__` prefix. The double qoute is there because it is just syntax suggar, which doesn't do anything but only to please static lsp analyzed (pyright, mypy, etc...)

**Weird interaction:**
Also there is weird interaction when we using `datastructure.__init__` like this, we can't access `datastruture` module anymore?
```python
# Runtime error code 1
from datastructure.__init__ import myQueue as dsiMyQueue

# Runtime error code 2
import datastructure.__init__ as dsi
dsi.Queue()
```

## Private method, class

Just prefix them with `__`, this help LSP not exposing them. It could in some case cause run-time error if we force our way to accessing those private method/class.
