# Scope Quiz

```python
pi = 3.14

def get_area_of_circle(radius):
    area = pi * radius * radius
    return area
```

## Question

Can the `area` variable inside the `get_area_of_circle` function be accessed outside of the functions

## Answer

No, variables defined inside a function are not accessible outside of it

Python does prevent this interaction, `area` is defined inside `get_area_of_circle` and will be unacessable when the function is finish executing
