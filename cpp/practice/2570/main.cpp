#include <bits/stdc++.h>
#include <fstream>
#include <ostream>

using namespace std;

class Solution {
public:
  vector<vector<int>> mergeArrays(vector<vector<int>> &nums1,
                                  vector<vector<int>> &nums2) {
    int p1 = 0;
    int p2 = 0;
    vector<vector<int>> res;
    // Assume the worst case capacity
    res.reserve(nums1.size() + nums2.size());
    while (p1 < nums1.size() || p2 < nums2.size()) {
      // Our debug line
      printf("[INFO] p1:%d, p2:%d, res:[%d,%d]\n", p1, p2,
             res.size() > 0 ? res.back()[0] : 0,
             res.size() > 0 ? res.back()[1] : 0);

      if (p1 >= nums1.size()) {
        res.push_back(nums2[p2]);
        p2++;
        continue;
      }

      if (p2 >= nums2.size()) {
        res.push_back(nums1[p1]);
        p1++;
        continue;
      }

      if (nums1[p1][0] == nums2[p2][0]) {
        res.push_back(nums1[p1]);
        res[res.size() - 1][1] = res[res.size() - 1][1] + nums2[p2][1];
        p1++;
        p2++;
      } else {
        if (nums1[p1][0] < nums2[p2][0]) {
          res.push_back(nums1[p1]);
          p1++;
        }

        if (nums1[p1][0] > nums2[p2][0]) {
          res.push_back(nums2[p2]);
          p2++;
        }
      }
    }
    return res;
  }
};

int main(int argc, char *argv[]) {
  auto a = new Solution();
  int n, m;
  ifstream f("in.txt", ios_base::in);
  f >> n >> m;
  vector<vector<int>> nums1;
  vector<vector<int>> nums2;
  nums1.reserve(n);
  nums2.reserve(n);
  for (int i = 0; i < n; i++) {
    int tmp, tmp2;
    f >> tmp >> tmp2;
    nums1.push_back(vector<int>({tmp, tmp2}));
  }
  for (int i = 0; i < m; i++) {
    int tmp, tmp2;
    f >> tmp >> tmp2;
    nums2.push_back(vector<int>({tmp, tmp2}));
  }

  a->mergeArrays(nums1, nums2);
  return 0;
}
