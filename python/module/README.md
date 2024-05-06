# Module

Python support for module (import, code file split, etc...)

## Python import - using other file method and class

There is a lot of way, here is a list with example

1. Import wanted function directly
    ```python
    import datastructure.myQueue
    from datastructure.myQueue import Queue
    # import <FolderPath>.<File>.<Class>

    # Now we can use Queue that have been decleare from datastructure/myQueue.py
    q1 = Queue()
    ```

    In `python` a `<File>` is call a `<Module>`

2. Import a Module and access their method/class through it
    ```python
    import datastructure.myQueue as q
    # import <FolderPath>.<File>.<Class>

    q2 = q.Queue()
    ```

3. Import a folder `datastructure/` directly
    ```python
    import datastructure
    # import <FolderPath>
    ```

    This require `__init__.py`, as the `import` will try to access that file if we try to import a directory. This will be the same as

    ```python
    import datastructure.__init__
    ```

## Project structure

Print `tree` of the project

```
#!tree
.
├── datastructure
│   ├── __init__.py
│   ├── myQueue.py
│   ├── myStack.py
└── main.py
```
