# B. Game with Colored Marbles

Alice and Bob play a game. There are n marbles, the i-th of them has color c[i].
The players take turns; Alice goes first, then Bob, then Alice again, then Bob
again, and so on.

During their turn, a player must take one of the remaining marbles and remove
it from the game. If there are no marbles left (all n marbles have been taken),
the game ends.

Alice's score at the end of the game is calculated as follows:

- She receives 1 point for every color x such that she has taken at least one
marble of that color;
- additionally, she receives 1 point for every color x such that she has taken
all marbles of that color (of course, only colors present in the game are
considered).

For example, suppose there are 5 marbles, their colors are [1,3,1,3,4], and the
game goes as follows: Alice takes the 1-st marble, then Bob takes the 3-rd
marble, then Alice takes the 5-th marble, then Bob takes the 2-nd marble,
and finally, Alice takes the 4-th marble. Then, Alice receives 4 points: 3 points
for having at least one marble for colors 1, 3 and 4, and 1 point for having all
marbles of color 4

> Note that this strategy is not necessarily optimal for both players.

Alice wants to maximize her score at the end of the game. Bob wants to minimize
it. Both players play optimally (i. e. Alice chooses a strategy which allows her
to get as many points as possible, and Bob chooses a strategy which minimizes
the amount of points Alice can get).

Calculate Alice's score at the end of the game.

## Input

The first line contains one integer t (1≤t≤1000) — the number of test cases.

Each test case consists of two lines:

- the first line contains one integer n (1≤n≤1000) — the number of marbles;
- the second line contains n integers c1,c2,…,cn (1≤ci≤n) — the colors of the
marbles. 

Additional constraint on the input: the sum of n over all test cases does not
exceed 1000

## Output

For each test case, print one integer — Alice's score at the end of the game,
assuming that both players play optimally.

## White board

Asumming that we have one of each distinct color value vs a at least two color
marbel

    1 2 3 3 4 4

How should Alice act: color_1/2 gave 2 point, color_3/4 gave 1 point

Bob can stop Alice get 2 point by picking other color 1/2, color 3/4 doesn't
have any different meanning. Now increase the total marbel of the same color

    1 2 3 3 3 4 4 4

Bob only need to take one marbel from a color pool and he can effectivelly cost
Alice 1 point, so any above 2 color only need 1 specific turn for bob to minimized
the score Alice want to take.

Alice and Bob will instantly want to get 2 point worth of marbel no mater what.

The problem seem just like a math only (calculate-able result) or a simulator for
each of game turn for the best possible move. It better to be a greedy apprach
first

## Solve
