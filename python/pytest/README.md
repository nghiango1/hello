# Pytest

A framework that pretty print your assert

## Pytest rule

pytest will run all files of the form test_*.py or *_test.py in the current directory and its subdirectories. More generally, it follows standard test discovery rules.

## Raise helper

pytest can assert a `raise` (expected value of a Error/Exeption) via helper `pytest.raises()`

```py
# content of test_sysexit.py
import pytest

def test_mytest():
    with pytest.raises(SystemExit):
        # We can use   ^^^^^^^^^^ any type of Exeptions here
        f()
```
