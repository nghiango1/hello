#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int n;
vector<int> arr;

int main() {
  cin >> n;
  for (int i = 0; i < n; i++) {
    int temp;
    cin >> temp;
    arr.push_back(temp);
  }

  int x = *max_element(arr.begin(), arr.end());
  cout << x << '\n';

  sort(arr.begin(), arr.end(),
       [](const int &l, const int &r) { return l < r; });

  int m = n / 2;
  // Get the index or target=arr[m]
  int lower = lower_bound(arr.begin(), arr.end(), arr[m]) - arr.begin();
  int upper = upper_bound(arr.begin(), arr.end(), arr[m]) - arr.begin();

  for (int i = lower; i <= upper; i++) {
    printf("[INFO] i:%d, arr[%d]:%d\n", i, i, arr[i]);
  }
  return 0;
}
