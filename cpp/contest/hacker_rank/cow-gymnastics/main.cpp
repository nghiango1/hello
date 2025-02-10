#include <bits/stdc++.h>

using namespace std;

void debug(const char *format, ...) {
  va_list args;
  va_start(args, format);
  //vprintf(format, args);
  va_end(args);
}

int main() {
  int k, n;
  cin >> k;
  cin >> n;
  debug("[info] n: %d, k: %d\n", n, k);

  vector<vector<int>> ranks(k, vector<int>(n+1));

  for (int i = 0; i < k; i++) {
    int temp;
    for (int j = 1; j <= n; j++) {
      cin >> temp;
      ranks[i][temp] = j;
    }
  }

  for (int i = 0; i < k; i++) {
    for (int j = 1; j <= n; j++) {
      debug("[info] rank[%d][%d] = %d\n", i, j, ranks[i][j]);
    }
  }

  int result = 0;
  for (int i = 1; i <= n; i++) {
    for (int j = i + 1; j <= n; j++) {
      debug("[info] check pair %d, %d, curr result:%d\n", i, j, result);
      int check = 0;
      result +=1;
      for (int session = 0; session < k; session++) {
        if (ranks[session][i] == ranks[session][j]) {
          result -=1;
          debug("[info] bad pair %d, %d: session[%d] result %d %d\n", i, j, session, ranks[session][i], ranks[session][j]);
          break;
        }
        if (check == 0) {
          check = ranks[session][i] < ranks[session][j] ? -1 : 1;
          continue;
        }
        if (check == 1 and ranks[session][i] < ranks[session][j]) {
          result -=1;
          debug("[info] bad pair %d, %d: session[%d] result %d %d\n", i, j, session, ranks[session][i], ranks[session][j]);
          break;
        }
        if (check == -1 and ranks[session][i] > ranks[session][j]) {
          result -=1;
          debug("[info] bad pair %d, %d: session[%d] result %d %d\n", i, j, session, ranks[session][i], ranks[session][j]);
          break;
        }
      }
    }
  }
  cout << result << '\n';

  return 0;
}
