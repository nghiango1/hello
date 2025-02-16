#include <bits/stdc++.h>
using namespace std;

void debug(const char *format, ...) {
  va_list args;
  va_start(args, format);
  vprintf(format, args);
  va_end(args);
}

int n, b;
vector<pair<int, int>> cows;
vector<int> valid_a;
vector<int> valid_b;

void generate() {
  valid_a.reserve(n + 1);
  valid_b.reserve(n + 1);
  valid_a.push_back(0);
  valid_b.push_back(0);
  for (int i = 0; i < n; i++) {
    valid_a.push_back(cows[i].first + 1);
    valid_b.push_back(cows[i].second + 1);
  }
}

int split(int a, int b) {
  vector<int> group(4, 0);
  for (int i = 0; i < n; i++) {
    // Extra checking if something can goes wrong
    if (cows[i].first == a || cows[i].second == b) {
      debug("[INFO] Split (%d, %d) collision with cows (%d, %d)\n", a, b,
            cows[i].first, cows[i].second);
      throw runtime_error(
          "a,b isn't even value and collision with existed cow");
    }

    if (cows[i].first < a and cows[i].second < b) {
      group[0] += 1;
    }
    if (cows[i].first < a and cows[i].second > b) {
      group[1] += 1;
    }
    if (cows[i].first > a and cows[i].second < b) {
      group[2] += 1;
    }
    if (cows[i].first > a and cows[i].second > b) {
      group[3] += 1;
    }
  }

  debug("[INFO] split(%d, %d), bottop-left:%d,  top-left:%d, bottop-right:%d, "
        "top-right:%d\n",
        a, b, group[0], group[1], group[2], group[3]);
  return *max_element(group.begin(), group.end());
};

int find() {
  int result = -1;
  for (int i = 0; i < valid_a.size(); i++) {
    for (int j = 0; j < valid_b.size(); j++) {
      if (result == -1) {
        result = split(valid_a[i], valid_b[j]);
      } else {
        result = min(result, split(valid_a[i], valid_b[j]));
      }
      debug("[INFO] result:%d\n", result);
    }
  }
  return result;
};

int main() {
  cin >> n >> b;
  debug("[INFO] n:%d, b:%d\n", n, b);
  cows.reserve(n);
  for (int i = 0; i < n; i++) {
    int x, y;
    cin >> x >> y;
    debug("[INFO] cow_i:%d, x:%d, y:%d\n", i, x, y);
    cows.push_back({x, y});
  }
  generate();
  cout << find() << '\n';
  return 0;
}
