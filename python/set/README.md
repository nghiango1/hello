# Set

## Initilization

Python set initilization and define is

```python
s = set()
```

Or directly just like `dict()`, but this need to have some default value. The
REPL will assumming `{}` a `dict()` without any init value provided

```python
s = {1, 2, 3}
```

Example session:

```
→ python
Python 3.11.11 (main, Dec  3 2024, 17:20:40) [GCC 13.3.0] on linux
Type "help", "copyright", "credits" or "license" for more information.
>>>  a= {}
  File "<stdin>", line 1
    a= {}
IndentationError: unexpected indent
>>> a = {}
>>> a.add(1)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
AttributeError: 'dict' object has no attribute 'add'
>>> a.add(2)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
AttributeError: 'dict' object has no attribute 'add'
>>> a = {1}
>>> a.add(1)
>>> a.add(2)
>>> a
{1, 2}
>>> type(a)
<class 'set'>
>>>
```

## Usage

In normal use case, `add` an `remove` is more than enough

```python
s = {1, 2}
s.remove(1)
s.add(3)
```

Some advange useage is `set` intersect or merge

- Merge using `|` operator
- Intersect using `&` operator
- Xor operator can also be use, which as you can expected result which remove
  all collision element of the two `set`

```
>>> a = {1, 2}
>>> b = {2, 3, 4}
>>> a | b
{1, 2, 3, 4}
>>> a & b
{2}
>>> a ^ b
{1, 3, 4}
```

## Example

Some leetcode problem which use `set`
