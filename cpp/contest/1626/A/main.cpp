#include <iostream>
#include <string>
#include <vector>

std::string solve(const std::string inputString) {
  std::string result;
  std::vector<int> count({});
  // lower case character only
  count.resize(26);
  for (int i = 0; i < 26; i++) {
    count[i] = 0;
  }
  // lower case character count
  for (auto c : inputString) {
    count[c - 'a'] += 1;
  }
  // Create the result string
  for (int i = 0; i < 26; i++) {
    if (count[i] == 2) {
      result.insert(result.end(), i + 'a');
      result.insert(result.end(), i + 'a');
    } else if (count[i] == 1) {
      result.insert(result.end(), i + 'a');
    }
  }
  return result;
}

int main() {
  int t;
  std::string s;
  std::cin >> t;
  // Skip the remaining input line
  std::getline(std::cin, s);
  for (int i = 0; i < t; i++) {
    std::getline(std::cin, s);
    std::cout << solve(s) << std::endl;
  }
  return 0;
}
