# Unit Tests

A unit test is just an automated program that tests a small "unit" of code. Usually a function or two.

As of prior lessons, <https://boot.dev> only check the program console output (text printed). Now and onward, there will be additional `main_test.py` file containning the unit tests. These new unit-test-style lessions will call the functions in your code with different arguments and expect certain return value. If the code returns the correct value, it consider pass. If it doesn't however, you will fail.

This allow you to:

- More realistic, cover more case for each requirement and make sure the code works as expected.
- Leave `print` debug code, as it will only check for function `return` value when you summit, unlike output base lessons.

## Assignment

Complete the `total_xp` function, it accepts two integers as input:

- `level`
- `xp_to_add`

There are 100 xp per level. `total_xp` should convert the current `level` to xp then add this current xp to the `xp_to_add` argument and return the player's total xp. For example:

- If a player is level 1 and gains 100 xp, they have 200 total xp.
- If a player is level 2 and gains 250 xp, they have 450 total xp.
- If a player is level 170 and gains 590 xp, they have 17590 total xp.

> Replace `pass` keyword in the code
> Take a look at `main_test.py`, you can read it but can't edit them

From now, `code` directory contain the original `main.py` code, `answer` now contain `main.py` that have been modify

```python
def total_xp(level, xp_to_add):
    pass
```
