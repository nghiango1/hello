# A. Equidistant Letters

You are given a string s, consisting of lowercase Latin letters. Every letter
appears in it no more than twice.

Your task is to rearrange the letters in the string in such a way that for each
pair of letters that appear exactly twice, the distance between the letters in
the pair is the same. You are not allowed to add or remove letters.

It can be shown that the answer always exists. If there are multiple answers,
print any of them.

## Input

The first line contains a single integer t (1≤t≤10**3) — the number of testcases.

Each testcase consists of a non-empty string s, consisting of lowercase Latin
letters. Every letter appears in the string no more than twice. The length of
the string doesn't exceed 52.

## Output

For each testcase, print a single string. Every letter should appear in it the
same number of times as it appears in string s

For each pair of letters that appear exactly twice, the distance between the
letters in the pair should be the same.

If there are multiple answers, print any of them.

## Whiteboard

We have 10**3 * 52 ~ O(N^2) time limit

The test case provided is quite random, but as for the re-arangement, any valid
answer is accepted so we can go for the most simple one. Which every letter
appear than twice is set to be next each other

## Solve

```cpp

```
