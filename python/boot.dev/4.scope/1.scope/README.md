# Scope

Scope is refered to where a variable or function name is available to be used.

## Assignment

Fix the code in line 10 in `3-4-code.py`

```python
def get_max_health(modifier, level):
    return modifier * level


my_modifier = 5
my_level = 10

## Modify here

max_health = get_max_health(modifier, level)

## End modify

print(f"max_health is: {max_health}")
```

## Answer

Running the code throw

```
Traceback (most recent call last):
  File "/home/ylong/workspace/hello/python/boot.dev/chapter3/3-4-code.py", line 10, in <module>
    max_health = get_max_health(modifier, level)
                                ^^^^^^^^
NameError: name 'modifier' is not defined. Did you mean: 'my_modifier'?
```

Fix, change

```python
max_health = get_max_health(my_modifier, my_level)
```
