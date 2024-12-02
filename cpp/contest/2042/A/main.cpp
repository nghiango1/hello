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

// Revert the sort cmp for largest to lowest sort
bool cmp(const int &left, const int &right){
  return left > right;
}

int solve(const int &n, const int &k, std::vector<int> &arr) {
  std::sort(arr.begin(), arr.end(), cmp);
  // debugArray(arr);
  int sum = 0;
  for (auto v : arr){
    if (sum + v > k) {
      break;
    }

    sum += v;
  }
  return k - sum;
}

void test() {
  // TODO
}

int main() {
  int t;
  std::cin >> t;
  for (int i = 0; i < t; i++) {
    int n;
    int k;
    // 10 ** 7 still in int range
    std::vector<int> arr({});

    std::cin >> n >> k;
    arr.resize(n);

    for (int index = 0; index < n; index++) {
      std::cin >> arr[index];
    }
    std::cout << solve(n, k, arr) << std::endl;
  }
  return 0;
}
