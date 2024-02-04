ref: https://leetcode.com/problems/is-subsequence

# Pow 10 ** 9

This is a problems in Leetcode:

> Given two strings `s` and `t`, return `true` if `s` is a subsequence of `t`, or `false` otherwise.

This specifically dedicated to the follow up: Suppose there are lots of incoming `s`, say `s_1, s_2, ..., s_k` where `k >= 10**9`, and you want to check one by one to see if t has its subsequence. In this scenario, how would you change your code?

## What happening here

Incase you don't know, 10 ** 9 = 1_000_000_000. This a a masive number, and could only be done in competitive time (sub 1s) only with sub `O(n)` solution.

This isn't possible at all when we have to read the input at least one. Let assume that we read the input while also preprocess in O(n) time or O(n log n) won't be count as the computation time. This leave us with some possible optimization:
- **gorountine/multi thread**
- cache `s` string
- ...

I have a more indepth walk through in [here](https://ylsama.github.io/notes/Leetcode%20100-1000/392.%20Is%20Subsequence/)
