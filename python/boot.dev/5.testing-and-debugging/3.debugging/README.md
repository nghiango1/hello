# Debugging

The process of writing code and testing it before deploy it to user

- At Boot.dev, the `Run` button is for debugging.
- The `Submit` button mimics the idea of publishing your code for production use.

## Assignment

Complete the `take_magic_damage` function

```python
def take_magic_damage(health, resist, amp, spell_power):
    pass
```

## Answer

We follow the requirement:

- `new_health` start with the same value vs `health`
- `max_damage` calculate by multiply `spell_power` with `amp`
- `actual_damage` calculate by subtract `max_damage` with `resist` value
- Finally, `new_health` is apply the `actual_damage` then being return

```python
def take_magic_damage(health, resist, amp, spell_power):
    new_health = health
    max_damage = spell_power * amp
    actual_damage = max_damage - resist
    new_health -= actual_damage
    return new_health
```
