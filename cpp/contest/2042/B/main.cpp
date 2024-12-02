#include <algorithm>
#include <iostream>
#include <vector>

void debugArray(const std::vector<int> &arr) {
  printf("[INFO] array = ");
  for (auto v : arr) {
    printf("%d, ", v);
  }
  printf("\n");
}

int solve(const int &n, std::vector<int> &arr) {
  std::vector<int> count({});
  count.resize(n);
  for (int i = 0; i < n; i++) {
    count[i] = 0;
  }
  // as 1 <= color <= n
  for (auto c : arr) {
    count[c - 1] += 1;
  }

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

  // printf("[INFO] Total single mable with it own different color %d\n", single);
  score += (single / 2) * 2 + (single % 2) * 2;

  return score;
}

void test() {
  // TODO
}

int main() {
  int t;
  std::cin >> t;
  for (int i = 0; i < t; i++) {
    int n;
    // 10 ** 7 still in int range
    std::vector<int> arr({});

    std::cin >> n;
    arr.resize(n);

    for (int index = 0; index < n; index++) {
      std::cin >> arr[index];
    }
    std::cout << solve(n, arr) << std::endl;
  }
  return 0;
}
