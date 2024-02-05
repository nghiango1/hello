# Two-crystal-ball search problem

Given 2 crystal balls, and a `n` height buildings. Find the exact floor of the building where if you drop the ball from that floor and above, the crystal ball will break.

Note: You only have two balls, which mean if both break, you can't test it again anymore

## The answer

Quick and easy way to done this is:
- The first ball we will try and skip many floor as possible to narrowing down our detail search
- The second ball will need to go from the last position where the fisrt ball was dropped and did not break

The best way is jumping in sqrt(n), this allowing both step (even in the worst possible outcome) will take only O(sqrt n) time complexity to complete

## The code

`two_crystal_ball.c` this containing these `c` languages features:
- global variables
- function
- function ref as other function argument
- local (main function, function only) variables
- library call
