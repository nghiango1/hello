# Two Lesson Types

As of prior lessons, <https://boot.dev> only check the program console output (text printed). Now and onward, there will be additional `main_test.py` file containning the unit tests. These new unit-test-style lessions will call the functions in your code with different arguments and expect certain return value. If the code returns the correct value, it consider pass. If it doesn't however, you will fail.

This allow you to:

- More realistic, cover more case for each requirement and make sure the code works as expected.
- Leave `print` debug code, as it will only check for function `return` value when you summit, unlike output base lessons.

## Assignment

What type of lessons can you safely summit with your debug print statements left in your code?

- Unit test
- Console output

## Answer

Unit test

> Console output lession must match the expected output exactly to pass
