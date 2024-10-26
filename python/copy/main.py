#!/bin/python3
import copy


class A:
    def __init__(self, x):
        self.x = x
        self.arr = [1, 2]


# we can use id to access object's memory address
aObj = A(2)

print(id(aObj))

# this is just tranfered the reference
bObj = aObj
print("Checking object assign '=' operator", id(aObj) == id(bObj))

# this is create new object
bObj = copy.copy(aObj)
print("Checking `copy.copy` builtin module method", id(aObj) == id(bObj))

# But still kept it's reference
print("Checking `copy.copy` builtin module method", id(aObj.arr) == id(bObj.arr))

# this create new object and all nested object also being copy
bObj = copy.deepcopy(aObj)
print("Checking `copy.deepcopy` builtin module method", id(aObj) == id(bObj))

# But still kept it's reference
print("Checking `copy.deepcopy` builtin module method",
      id(aObj.arr) == id(bObj.arr))
