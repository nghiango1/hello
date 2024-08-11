#include <iostream>
#include <stack>
#include <string>
#include <vector>

using namespace std;

#define int64 long long
#define MODULE 1000000007
#define MAX_NODE 70000
#define MAX_SPECIAL 70000

// class Group:
//     def __init__(self, member: List[int], block: List[int], id: int):
//         self.id = id
//         self.member = member
//         self.block = block
class Group {
public:
  int id = 0;
  int *block;
  int blockLen = 0;
  int *member;
  int memberLen = 0;

  Group(int totalNode) {
    block = (int *)malloc(totalNode * sizeof(int));
    member = (int *)malloc(totalNode * sizeof(int));
  }

  ~Group() {
    delete (block);
    delete (member);
  }
};

// class Tree:
//     def __init__(self, totalNode):
//         # Input from the problem
//         self.totalNode = totalNode
//         self.adj: Dict[int, List[int]] = {}
//         self.special: List[int] = [False] * totalNode
//
//         # Calculate pre count within O(n)
//         self.preCount: List[int] = [-1] * totalNode
//
//         # Store relevant infomation
//         self.groupId: List[int] = [-1] * totalNode
//         self.currGroupId = -1
//         self.group: List[Group] = []
class Tree {
public:
  int totalNode = 0;
  int **adj;
  int *adjLen;
  vector<Group *> group;
  int *groupId;
  int *special;
  int *preCount;

  Tree(int totalNode) {
    this->totalNode = totalNode;
    special = (int *)malloc(totalNode * sizeof(int));
    groupId = (int *)malloc(totalNode * sizeof(int));
    preCount = (int *)malloc(totalNode * sizeof(int));
    adj = (int **)malloc(totalNode * sizeof(int *));
    adjLen = (int *)malloc(totalNode * sizeof(int));
    for (int i = 0; i < totalNode; i++) {
      special[i] = false;
      adj[i] = (int *)malloc(totalNode * sizeof(int));
      adjLen[i] = 0;
    }
  }

  ~Tree() {
    for (int i = 0; i < totalNode; i++) {
      delete (adj[i]);
    }
    delete (adj);
    delete (adjLen);
    for (int i = 0; (unsigned)i < group.size(); i++) {
      delete (group[i]);
    }
    group.clear();
    delete (special);
    delete (groupId);
    delete (preCount);
  }
};

//     def addEdge(self, u: int, v: int):
//         u = u - 1
//         v = v - 1
//         if u not in self.adj:
//             self.adj[u] = []
//         if v not in self.adj:
//             self.adj[v] = []
//         self.adj[u].append(v)
//         self.adj[v].append(u)
int addEdge(Tree *tree, int u, int v) {
  u = u - 1;
  v = v - 1;
  tree->adj[u][tree->adjLen[u]] = v;
  tree->adjLen[u] += 1;
  tree->adj[v][tree->adjLen[v]] = u;
  tree->adjLen[v] += 1;
  return 0;
}

//     def selfTraversal(self, start=0):
//         parent = [-1] * self.totalNode
//         stack = [start]
//         while stack.__len__() > 0:
//             curr = stack.pop()
//             for next in self.adj[curr]:
//                 if next == parent[curr]:
//                     continue
//                 parent[next] = curr
//                 stack.append(next)
int selfTraversal(Tree *tree, int start = 0) {
  int parent[MAX_NODE] = {0};
  for (int i = 0; i < tree->totalNode; i++) {
    parent[i] = -1;
  }

  cout << "Traversal: ";
  stack<int> stack;
  stack.push(start);
  while (not stack.empty()) {
    int curr = stack.top();
    cout << curr << ", ";
    stack.pop();

    for (int i = 0; i < tree->adjLen[curr]; i++) {
      int next = tree->adj[curr][i];
      if (next == parent[curr])
        continue;
      parent[next] = curr;
      stack.push(next);
    }
  }
  cout << "\n";

  return 0;
}
//     def calSpecial(self, start):
//         # Take it self
//         res = 1
//
//         # And every group around it, could cost O(n)? but it quite minimal
//         for next in self.adj[start]:
//             if not self.special[next]:
//                 res += self.group[self.groupId[next]].member.__len__()
//         return res
int calSpecial(Tree *tree, int start) {
  int res = 1;

  for (int i = 0; i < tree->adjLen[start]; i++) {
    int next = tree->adj[start][i];
    if (not tree->special[next])
      res += tree->group[tree->groupId[next]]->memberLen;
  }

  return res;
}

//     def calNonSpecial(self, start):
//         startGroup = self.group[self.groupId[start]]
//
//         # Take all the block around it
//         res = startGroup.block.__len__()
//
//         # Then traversal to closest group connected to it. Could cost O(n**2)
//         # But it also quite minimal
//         for closeSpecialNode in startGroup.block:
//             for closeNode in self.adj[closeSpecialNode]:
//                 if self.special[closeNode]:
//                     continue
//                 if self.groupId[closeNode] == startGroup.id:
//                     continue
//                 closeNodeGroup = self.group[self.groupId[closeNode]]
//                 res += closeNodeGroup.member.__len__()
//         return res
int calNonSpecial(Tree *tree, int start) {
  Group *startGroup = tree->group[tree->groupId[start]];
  int res = startGroup->blockLen;

  for (int i = 0; i < startGroup->blockLen; i++) {
    int closeSpecialNode = startGroup->block[i];
    for (int j = 0; j < tree->adjLen[closeSpecialNode]; j++) {
      int closeNode = tree->adj[closeSpecialNode][j];
      if (tree->special[closeNode])
        continue;
      if (tree->groupId[closeNode] == startGroup->id)
        continue;
      Group *closeNodeGroup = tree->group[tree->groupId[closeNode]];
      res += closeNodeGroup->memberLen;
    }
  }

  return res;
}

int nonSpecialTraversal(Tree *tree, int start) {
  Group *temp = new Group(tree->totalNode);
  temp->member[temp->memberLen] = start;
  temp->memberLen += 1;

  int parent[tree->totalNode];
  for (int i = 0; i < tree->totalNode; i++) {
    parent[i] = -1;
  }

  // cout << "Traversal: ";
  stack<int> stack;
  stack.push(start);
  while (not stack.empty()) {
    int curr = stack.top();
    // cout << curr << ", ";
    stack.pop();

    for (int i = 0; i < tree->adjLen[curr]; i++) {
      int next = tree->adj[curr][i];
      if (next == parent[curr])
        continue;
      parent[next] = curr;

      //                 if not self.special[next]:
      //                     # Non specical node is a member of the group
      //                     member.append(next)
      //                     stack.append(next)
      //                 else:
      //                     # Specical nodes is the border of the group
      //                     block.append(next)
      if (not tree->special[next]) {
        temp->member[temp->memberLen] = next;
        temp->memberLen += 1;
        stack.push(next);
      } else {
        temp->block[temp->blockLen] = next;
        temp->blockLen += 1;
      }
    }
  }
  // cout << "\n";

  //         # Storing group
  //         self.currGroupId += 1
  //         self.group.append(Group(member, block, self.currGroupId))
  //
  //         # Update groupId for each member, needed for calculating later
  //         for curr in member:
  //             self.groupId[curr] = self.currGroupId
  temp->id = tree->group.size();
  tree->group.push_back(temp);
  for (int i = 0; i < temp->memberLen; i++) {
    int curr = temp->member[i];
    tree->groupId[curr] = temp->id;
  }

  return 0;
}

//     def initPreCount(self):
//         for i in range(self.totalNode):
//             if self.special[i]:
//                 continue
//             if self.groupId[i] == -1:
//                 self.nonSpecialTraversal(i)
//
//         for i in range(self.totalNode):
//             if self.special[i]:
//                 self.preCount[i] = self.calSpecial(i)
//             else:
//                 self.preCount[i] = self.calNonSpecial(i)
int initPreCount(Tree *tree) {
  for (int i = 0; i < tree->totalNode; i++) {
    tree->groupId[i] = -1;
  }

  for (int i = 0; i < tree->totalNode; i++) {
    if (tree->special[i])
      continue;
    if (tree->groupId[i] == -1) {
      nonSpecialTraversal(tree, i);
    }
  }

  for (int i = 0; i < tree->totalNode; i++) {
    if (tree->special[i])
      tree->preCount[i] = calSpecial(tree, i);
    else
      tree->preCount[i] = calNonSpecial(tree, i);
  }
  return 0;
}

string dumpGroup(Group *group) {
  string res = "{ ";
  res.append("id:");
  res.append(to_string(group->id));
  res.append(", member:[");
  for (int i = 0; i < group->memberLen; i++) {
    if (i != 0) {
      res.append(", ");
    }
    res.append(to_string(group->member[i]));
  }
  res.append("]");
  res.append(", block:[");
  for (int i = 0; i < group->blockLen; i++) {
    if (i != 0) {
      res.append(", ");
    }
    res.append(to_string(group->block[i]));
  }
  res.append("]");
  res.append(" }");
  return res;
}

//     def __str__(self):
//         res = "{ "
//         res += f"adj = {self.adj}"
//         res += f", special = {self.special}"
//         res += f", totalNode = {self.totalNode}"
//         res += f", preCount = {self.preCount}"
//         res += " }"
//         return res
//
//     def dump(self):
//         print(self.__str__())
//
string dump(Tree *tree, string name = "Not provided") {
  string res = "";
  res = "{ ";
  res.append("\n  name:");
  res.append(name);
  res.append(",\n  totalNode:");
  res.append(to_string(tree->totalNode));
  res.append(",\n  special:[");
  for (int i = 0; i < tree->totalNode; i++) {
    if (i != 0) {
      res.append(", ");
    }
    if (tree->special[i])
      res.append("TRUE");
    else
      res.append("FALSE");
  }
  res.append("]");
  res.append(",\n  adj:[\n");
  for (int i = 0; i < tree->totalNode; i++) {
    if (i != 0) {
      res.append(",\n");
    }
    res.append("    (" + to_string(i) + "):[");
    for (int j = 0; j < tree->adjLen[i]; j++) {
      if (j != 0) {
        res.append(", ");
      }
      res.append(to_string(tree->adj[i][j]));
    }
    res.append("]");
  }
  res.append("\n  ]");
  if (tree->group.size() > 0) {
    res.append(",\n  group:[\n");
    for (int i = 0; (unsigned)i < tree->group.size(); i++) {
      if (i != 0) {
        res.append(",\n");
      }
      res.append("    (" + to_string(i) + "):");
      res.append(dumpGroup(tree->group[i]));
      res.append("");
    }
    res.append("\n  ]");
  }
  res.append("\n}");
  return res;
}

// def readinput():
//     global treeA, treeB
//
//     temps = input().split()
//     n_a, m_a = int(temps[0]), int(temps[1])
//     treeA = Tree(n_a)
//
//     temps = input().split()
//     for i in temps:
//         treeA.special[int(i) - 1] = True
//
//     for _ in range(n_a - 1):
//         temps = input().split()
//         u, v = int(temps[0]), int(temps[1])
//         treeA.addEdge(u, v)
//
//     temps = input().split()
//     n_b, m_b = int(temps[0]), int(temps[1])
//     treeB = Tree(n_b)
//
//     temps = input().split()
//     for i in temps:
//         treeB.special[int(i) - 1] = True
//
//     for _ in range(n_b - 1):
//         temps = input().split()
//         u, v = int(temps[0]), int(temps[1])
//         treeB.addEdge(u, v)
int readinput(Tree **treeA, Tree **treeB) {
  int temp, temp2, n_a, m_a, n_b, m_b;
  cin >> n_a;
  cin >> m_a;

  *treeA = new Tree(n_a);
  for (int i = 0; i < m_a; i++) {
    cin >> temp;
    (*treeA)->special[temp - 1] = true;
  }

  for (int i = 0; i < n_a - 1; i++) {
    cin >> temp;
    cin >> temp2;

    addEdge(*treeA, temp, temp2);
  }

  cin >> n_b;
  cin >> m_b;

  *treeB = new Tree(n_b);
  for (int i = 0; i < n_b; i++) {
    (*treeB)->special[i] = false;
  }

  for (int i = 0; i < m_b; i++) {
    cin >> temp;
    (*treeB)->special[temp - 1] = true;
  }

  for (int i = 0; i < n_b - 1; i++) {
    cin >> temp;
    cin >> temp2;

    addEdge(*treeB, temp, temp2);
  }

  return 0;
}

// def dumpInput():
//     global treeA, treeB
//     treeA.dump()
//     treeB.dump()
int dumpInput(Tree *treeA, Tree *treeB) {
  printf("%s\n", dump(treeA, "A").c_str());
  printf("%s\n", dump(treeB, "B").c_str());

  return 0;
}

// def solve():
//     global treeA, treeB, MODULE
//     treeA.initPreCount()
//     treeB.initPreCount()
//
//     # Try to do it in O(n)
//     res = 0
//     temp1 = 0
//     for i in range(treeB.totalNode):
//         temp1 += treeB.preCount[i]
//         temp1 %= MODULE
//
//     temp2 = 0
//     for i in range(treeB.totalNode):
//         temp2 += (i+1) * treeB.preCount[i]
//         temp2 %= MODULE
//
//     for i in range(treeA.totalNode):
//         res += treeA.preCount[i] * (temp2 + (i+1)*temp1) % MODULE
//         res %= MODULE
//
//     print(res)
int solve(Tree *treeA, Tree *treeB) {
  initPreCount(treeA);
  initPreCount(treeB);

  int64 res = 0;
  int64 temp1 = 0;
  for (int i = 0; i < treeB->totalNode; i++) {
    temp1 += treeB->preCount[i];
    temp1 %= MODULE;
  }
  int64 temp2 = 0;
  for (int i = 0; i < treeB->totalNode; i++) {
    temp2 += (i + 1) * treeB->preCount[i];
    temp2 %= MODULE;
  }
  for (int i = 0; i < treeA->totalNode; i++) {
    res += treeA->preCount[i] * (temp2 + (i + 1) * temp1) % MODULE;
    res %= MODULE;
  }

  cout << res << "\n";
  return 0;
}

// Only kept test 4
int test(int example = -1) {
  //         treeB = Tree(4)
  //         treeB.adj = {0: [1, 3], 1: [0], 3: [0, 2], 2: [3]}
  //         special = set([1, 2])
  //         for i in special:
  //             treeB.special[i] = True
  if (example < 0 or example == 4) {
    Tree *treeA = new Tree(8);
    addEdge(treeA, 1, 2);
    addEdge(treeA, 1, 3);
    addEdge(treeA, 1, 4);
    addEdge(treeA, 1, 5);
    addEdge(treeA, 1, 6);
    addEdge(treeA, 1, 7);
    addEdge(treeA, 1, 8);
    int special[4] = {0, 2, 4, 6};
    for (int i = 0; i < 4; i++)
      treeA->special[special[i]] = true;

    Tree *treeB = new Tree(4);
    addEdge(treeB, 1, 2);
    addEdge(treeB, 1, 4);
    addEdge(treeB, 4, 3);
    int specialB[2] = {1, 2};
    for (int i = 0; i < 2; i++)
      treeB->special[specialB[i]] = true;

    dumpInput(treeA, treeB);
    // selfTraversal(&treeA);
    // selfTraversal(&treeB);
    solve(treeA, treeB);
    dumpInput(treeA, treeB);
    delete treeA;
    delete treeB;
  }

  return 0;
}

// def main():
//     global treeA, treeB, piorityQueue
//
//     c = int(input())
//     for _ in range(c):
//         gc.collect()
//         readinput()
//         # dumpInput()
//         solve()
//         treeA.clear()
//         treeB.clear()
//
//
int trueMain() {
  int c;
  cin >> c;
  for (int i = 0; i < c; i++) {
    Tree *treeA = nullptr;
    Tree *treeB = nullptr;
    readinput(&treeA, &treeB);
    // dumpInput(treeA, treeB);
    solve(treeA, treeB);
    delete (treeA);
    delete (treeB);
  }
  return 0;
}

// if __name__ == "__main__":
//     test()
//     # main()
int main() {
  //test();
  trueMain();
  return 0;
}
