#include <bits/stdc++.h>
#include <cstdarg>
#include <cstdio>

using namespace std;

// Quick debug wrapper over printf
void debug(const char *__restrict format, ...) {
  va_list args;
  va_start(args, format);
#ifdef DEBUG
  vprintf(format, args);
#endif // DEBUG
  va_end(args);
}

int n = 0;
int resMin = 0, resMax = 0;

// Structure to contain Alice and Bob tree
class Tree {
public:
  vector<vector<int>> adj{};
  vector<int> color{};
  vector<int> parent{};
  vector<int> leaf{};

  void init(int n) {
    color.reserve(n);
    adj.reserve(n);
    parent.reserve(n);
    for (int i = 0; i < n; i += 1) {
      adj.push_back({});
      parent.push_back(-1);
    }
  }

  // Find parent from the input tree graph
  void traversal(int id = 0, int level = 0) {
    int curr = id;
    for (int i = 0; i < level; i++) {
      debug("--");
    }
    debug("%d (%d)\n", id, color[id]);
    bool isLeaf = true;
    for (auto child : adj[curr]) {
      if (parent[curr] == child)
        continue;
      parent[child] = curr;
      traversal(child, level + 1);
      isLeaf = false;
    }

    if (isLeaf) {
      leaf.push_back(curr);
    }
  }

  // Optimal play the game, using a prefered color while keepping the turn
  // to be maximize
  void playOptimal(int changeTurnColor, int priorityColor) {
    // turn, total, tree_state
    priority_queue<pair<int, int>> q;
    for (int id : leaf) {
      q.push({0, id});
    }
    int curr = 0;
  }
};

Tree Alice;
Tree Bob;

void solve() {
  // Find all parent node
  Alice.traversal();
  Bob.traversal();
}

int main() {
  scanf("%d", &n);
  debug("[INFO] n: %d\n", n);
  Alice.init(n);
  Bob.init(n);

  for (int i = 0; i < n; i += 1) {
    int t;
    scanf("%d", &t);
    Alice.color.push_back(t);
    debug("[INFO] marble's colorA[%d] = %d\n", i, Alice.color[i]);
  }

  for (int i = 0; i < n - 1; i += 1) {
    int u, v;
    scanf("%d%d", &u, &v);
    debug("[INFO] Edge (tree A): %d -> %d\n", u, v);
    Alice.adj[u - 1].push_back(v - 1);
    Alice.adj[v - 1].push_back(u - 1);
  }

  for (int i = 0; i < n; i += 1) {
    int t;
    scanf("%d", &t);
    Bob.color.push_back(t);
    debug("[INFO] Bob.color[%d] = %d\n", i, Bob.color[i]);
  }

  for (int i = 0; i < n - 1; i += 1) {
    int u, v;
    scanf("%d%d", &u, &v);
    debug("[INFO] Edge (tree B): %d -> %d\n", u, v);
    Bob.adj[u - 1].push_back(v - 1);
    Bob.adj[v - 1].push_back(u - 1);
  }

  solve();
  printf("%d %d\n", resMin, resMax);

  return 0;
}
