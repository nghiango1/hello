// String
// You are given a string s of length n consisting of 0 and/or 1. In one operation,
// you can select a non-empty subsequence t from s such that any two adjacent characters
// in t are different. Then, you flip each character of t (0 becomes 1 and 1 becomes 0).
// For example, if s=0––0101–––– and t=s1s3s4s5=0101, after the operation, s becomes 1––0010––––
// Calculate the minimum number of operations required to change all characters in s
// to 0
// Recall that for a string s=s1s2…sn, any string t=si1si2…sik (k≥1) where 1≤i1<i2<…<ik≤n
// is a subsequence of s.

#include <bits/stdc++.h>

using namespace std;

void debug(const char* format, ...) {
#ifndef ONLINE_JUDGE
    va_list args;
    va_start(args, format);
    vprintf(format, args);
    va_end(args);
#endif
}

int bit_count(string s) {
    int count = 0;
    for (char c : s) {
        if (c == '1') {
            count++;
        }
    }
    return count;
}

int main() {
    int t;
    string s;
    int res;

    scanf("%d\n", &t);
    for (int i = 0; i < t; i++) {
        getline(cin, s);
        debug("[INFO] s: %s\n", s.c_str());
        res = bit_count(s);
        debug("[INFO] result: %d\n", res);
        printf("%d\n", res);
    }
    return 0;
}