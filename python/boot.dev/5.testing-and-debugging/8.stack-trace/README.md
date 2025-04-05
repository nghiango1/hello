# Stack Trace

A stack trace (or "traceback") is a scary-looking error message that the Python interpreter prints to the console when it encounters certain problems

## Assigment

Fix the code

```python
def create_stats_message(strength, wisdom, dexterity):
      total = strength + wisdom + dexterity
    msg = f"You have {strength} strength, {wisdom} wisdom, and {dexterity} dexterity for a total of {total} stats.
    return msg
```

## Answer

Running cause this error message

```
→ python main-fix-0.py
  File "/home/ylong/workspace/hello/python/boot.dev/5.testing-and-debugging/8.stack-trace/answer/main-fix-0.py", line 3
    msg = f"You have {strength} strength, {wisdom} wisdom, and {dexterity} dexterity for a total of {total} stats.
                                                                                                                  ^
IndentationError: unindent does not match any outer indentation level
```

Fix? First is that at line 3, we have ... Meh, I see that indent level problem, also the string end doesn't have `"`, fix them both and the full unit test is passed

```python
def create_stats_message(strength, wisdom, dexterity):
    total = strength + wisdom + dexterity
    msg = f"You have {strength} strength, {wisdom} wisdom, and {dexterity} dexterity for a total of {total} stats."
    return msg
```
