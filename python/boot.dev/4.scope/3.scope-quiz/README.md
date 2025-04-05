# Scope Quiz

```python
pi = 3.14

def get_area_of_circle(radius):
    area = pi * radius * radius
    return area
```

## Question

Does the `get_area_of_circle` function have access to the `pi` variable?

## Answer

Yes, because the pi variable is in the global scope

> The others answer is wrong as:
>
> - functions **can't** alway access any variable, they only access those within they and their parents's scope
> - **No** is wrong, `pi` is declare in the global scope of the program, which mean `get_area_of_circle` can access it
