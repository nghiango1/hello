def get_max_health(modifier, level):
    return modifier * level


my_modifier = 5
my_level = 10

## Modify here

max_health = get_max_health(my_modifier, my_level)

## End modify

print(f"max_health is: {max_health}")
