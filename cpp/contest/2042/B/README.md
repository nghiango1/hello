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

Solely rely on greedy argo, Bob can only minimize Alice form picking any single
mable with distinct color on his turn, and stop Alice from getting all of others
at least two marble with the same color.

The code simulating the stragety for both player is that Alice and Bob take turn
to get the single distinct color mable. As Alice goes first, this stage of the
game result in total score

```
score += (single / 2) * 2 + (single % 2) * 2;
```

Where the next stage, every turn of Alice and Bob choose the same color marbel.
This allowing Alice can't never get 2 point for that color (getting all marbel
of the same color) but also allow Alice get at least 1 point for that color.

```
score += more_than_one
```

To simulate this game state and count all single color one, first thing I done
is count all total marbel of each color.

```cpp
{
    std::vector<int> count({});
    count.resize(n);
    for (int i = 0; i < n; i++) {
        count[i] = 0;
    }
    // as 1 <= color <= n
    for (auto c : arr) {
        count[c - 1] += 1;
    }
}
```

I even sort the thing, but it quite over kill. We only need another count for
single and more_than_one (I just update the score directly instead)

```cpp
{
    std::sort(count.begin(), count.end());
    // debugArray(count);
    int score = 0;
    int single = 0;
    for (int i = 0; i < n; i++) {
        if (count[i] == 0)
            continue;
        if (count[i] == 1) {
            single += 1;
            continue;
        }
        // All remainning color can only get 1 point
        // score += n - 1 - i + 1;
        // break;
        score += 1;
    }
}
```
