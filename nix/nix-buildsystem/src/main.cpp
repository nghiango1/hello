#include <iostream>
#include <vector>
#include "math.hpp"

using namespace std;

int main(int argc, char *argv[]) {
  int n;
  vector<int> arr;
  cin >> n;
  arr.resize(n);
  for (int i = 0; i < n; i++) {
    cin >> arr[i];
  }
  cout << sum(arr) << '\n';
  return 0;
}
