#include <iostream>
#include <queue>
#include <stdio.h>
#include <string>

using namespace std;

#define int64 long long

int64 t;
int64 n;
int64 minPath;
int64 maxPath;
int64 d[10][10][10];

// def readinput():
//     n = int(input())
//     d = []
//     curr = 0
//     for i in range(0, n**2, n):
//         d.append([])
//         for j in range(n):
//             temps = input().split()
//             d[curr].append([int(i) for i in temps])
//         curr += 1
//
//     return n, d
int readinput() {
  int64 temps;
  cin >> n;

  for (int64 curr = 0; curr < n; curr++) {
    for (int64 i = 0; i < n; i++) {
      for (int64 j = 0; j < n; j++) {
        cin >> temps;
        d[curr][i][j] = temps;
      }
    }
  }

  return 0;
}

// def setMark(mark: int, x: int):
//     return mark | (1 << x)
int64 setMark(int64 mark, int x) { return mark | 1 << x; }

// def getMark(mark: int, x: int):
//     return (mark & (1 << x)) >> x
int64 getMark(int64 mark, int x) { return (mark & (1 << x)) >> x; }

// def dumpMark(mark: int, n: int):
//     if mark == 0:
//         return "0" * n
//     return "0" * (n - mark.bit_length()) + bin(mark)[2:]
string dumpMark(int64 mark, int64 n) {
  string res = "";
  for (int64 i = 0; i < n; i++) {
    res.append(to_string(getMark(mark, n - i)));
  }
  return res;
}

class QueueData {
public:
  int64 length = 0;
  int64 city = -1;
  int64 cityMark = 0;
  int64 transpostMark = 0;
};

// def solve(n: int, d: List[List[int]]) -> Tuple[int, int]:
int solve() {
  //     minPath = None
  //     maxPath = 0
  minPath = -1;
  maxPath = 0;

  //     queue = deque()
  queue<QueueData> queue;

  //     # 1st city
  //     length = 0
  //     start = 0
  //     cityMark = 0
  //     tranpostMark = 0
  //     queue.append((length, start, cityMark, tranpostMark))
  QueueData start;
  start.length = 0;
  start.city = 0;
  start.cityMark = 0;
  start.transpostMark = 0;
  queue.push(start);

  //     while len(queue) > 0:
  //         currLength, currCity, currCityMark, currTranpostMark =
  //         queue.popleft() # print(f"currLength = {currLength}", end="") #
  //
  //         done = True
  //         for nextCity in range(1, n):
  //             if getMark(currCityMark, nextCity) == 1:
  //                 continue
  //
  //             done = False
  //             nextCityMark = setMark(currCityMark, nextCity)
  //             for nextTranpost in range(n):
  //                 if getMark(currTranpostMark, nextTranpost) == 1:
  //                     continue
  //                 nextTranpostMark = setMark(currTranpostMark, nextTranpost)
  //
  //                 # No path check
  //                 if d[nextTranpost][currCity][nextCity] == 0:
  //                     continue
  //
  //                 nextLength = currLength +
  //                 d[nextTranpost][currCity][nextCity]
  //                 queue.append(
  //                     (nextLength, nextCity, nextCityMark, nextTranpostMark))
  while (not queue.empty()) {
    QueueData curr = queue.front();
    queue.pop();

    bool done = true;
    for (int64 nextCity = 1; nextCity < n; nextCity++) {
      if (getMark(curr.cityMark, nextCity) == 1) {
        continue;
      }

      done = false;
      for (int64 nextTranpost = 0; nextTranpost < n; nextTranpost++) {
        if (getMark(curr.transpostMark, nextTranpost) == 1) {
          continue;
        }

        // No path check
        if (d[nextTranpost][curr.city][nextCity] == 0) {
          continue;
        }

        QueueData next;
        next.length = curr.length + d[nextTranpost][curr.city][nextCity];
        next.city = nextCity;
        next.cityMark = setMark(curr.cityMark, nextCity);
        next.transpostMark = setMark(curr.transpostMark, nextTranpost);
        queue.push(next);
      }
    }

    //         if done and getMark(currCityMark, 0) == 0:
    //             nextCity = 0
    //             done = False
    //
    //             nextCityMark = setMark(currCityMark, nextCity)
    //             for nextTranpost in range(n):
    //                 if getMark(currTranpostMark, nextTranpost) == 1:
    //                     continue
    //                 nextTranpostMark = setMark(currTranpostMark,
    //                 nextTranpost)
    //
    //                 # No path check
    //                 if d[nextTranpost][currCity][nextCity] == 0:
    //                     continue
    //
    //                 nextLength = currLength +
    //                 d[nextTranpost][currCity][nextCity]
    //
    //                 queue.append(
    //                     (nextLength, nextCity, nextCityMark,
    //                     nextTranpostMark))
    if (done and getMark(curr.cityMark, 0) == 0) {
      int64 nextCity = 0;
      done = false;

      for (int64 nextTranpost = 0; nextTranpost < n; nextTranpost++) {
        if (getMark(curr.transpostMark, nextTranpost) == 1) {
          continue;
        }

        // No path check
        if (d[nextTranpost][curr.city][nextCity] == 0) {
          continue;
        }

        QueueData next;
        next.length = curr.length + d[nextTranpost][curr.city][nextCity];
        next.city = nextCity;
        next.cityMark = setMark(curr.cityMark, nextCity);
        next.transpostMark = setMark(curr.transpostMark, nextTranpost);
        queue.push(next);
      }
    }

    //         if done:
    //             if minPath is None:
    //                 minPath = currLength
    //                 # print(
    //                 #     f"done = true, update minPath = {minPath}, trace =
    //                 {currTrace}")
    //             if minPath > currLength:
    //                 minPath = currLength
    //                 # print(
    //                 #     f"done = true, update minPath = {minPath}, trace =
    //                 {currTrace}")
    //             if maxPath < currLength:
    //                 maxPath = currLength
    //                 # print(
    //                 #     f"done = true, update maxPath = {maxPath}, trace =
    //                 {currTrace}")
    if (done) {
      if (minPath == -1)
        minPath = curr.length;
      if (minPath > curr.length)
        minPath = curr.length;
      if (maxPath < curr.length)
        maxPath = curr.length;
    }
  }

  if (minPath == -1)
    minPath = 0;
  return 1;
}

int test() {
  int64 mark = 0;
  int64 mark2 = setMark(mark, 2);
  string str = dumpMark(mark2, 10);
  printf("%s", str.c_str());
  return 0;
}

int dumpInput() {
  cout << "input\n";
  cout << "n: " << n << "\n";
  for (int64 k = 0; k < n; k++) {
    cout << "transpost " << k << ":\n";
    for (int64 i = 0; i < n; i++) {
      for (int64 j = 0; j < n; j++) {
        cout << d[k][i][j];
        if (j < n - 1) {
          cout << " ";
        }
      }

      cout << "\n";
    }
  }

  return 0;
}

// def main():
//     t = int(input())
//     for i in range(t):
//         n, d = readinput()
//         # print(f"n = {n}, d = {d}")
//         minPath, maxPath = solve(n, d)
//         print(minPath, maxPath)
int trueMain() {
  int64 t;
  cin >> t;
  for (int64 i = 0; i < t; i++) {
    readinput();
    // dumpInput();
    solve();
    printf("%lld %lld\n", minPath, maxPath);
  }
  return 0;
}

// if __name__ == "__main__":
//     # test()
//     main()
int main() {
  // test();
  trueMain();
}
