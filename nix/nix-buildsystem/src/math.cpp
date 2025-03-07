#include "math.hpp"

int sum(const vector<int> &arr) {
  int res = 0;
  for (int i = 0; i < arr.size(); i++) {
    res += arr[i];
  }
  return res;
}
