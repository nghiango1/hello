# 2246. Longest Path With Different Adjacent Characters

You are given a tree (i.e. a connected, undirected graph that has no cycles)
rooted at node 0 consisting of n nodes numbered from 0 to n - 1. The tree is
represented by a 0-indexed array parent of size n, where `parent[i]` is the
parent of node i. Since node 0 is the root, `parent[0] == -1`.

You are also given a string s of length n, where `s[i]` is the character
assigned to node i.

Return the length of the longest path in the tree such that no pair of adjacent
nodes on the path have the same character assigned to them.

Constraints:

-  `n == parent.length == s.length`
-  `1 <= n <= 10^5`
-  `0 <= parent[i] <= n - 1 for all i >= 1`
-  `parent[0] == -1`
-  parent represents a valid tree.
-  `s` consists of only lowercase English letters.


## Notes

Already done this problem with backtrack before, but with updated constraints
`n <= 10^5`, this mean at least the problem need to be solved within O(n log n)
time complexity

The ideal: Find the longest path for each node then merge it at parent

- We basically do brute force and try every possible combination
- Longest path on each node will contain that node, allow us to connect that node with current
parent node
- Each found path with each node will be updated to the result if it is longer

This mean we want to traverse the tree:
- Start at tree root
- Try to find the longest path on all child node (recursive)
  - This repeat, assuming child node is the new root
- Try to merge the possible path with root
  - Required to store `best_path` which start from the child node (so it can connect into root)
  - This should be store separately
  - We can greedy choose the 2 best path (longest and the next longest)
- Update result if the path is longer
  - Merge path can't be used to connect into root, so we can skip stored these extra data 
  - Only update result with merge path length > result

A lot of problems when handle this:

- How trace the longest path: For debug result, store through an O(n) space
- Merge logic: Need two best paths from current node's children.
- Separated from stored result when traverse the tree, which only need one best
path with current node as start node down to its children

Time complexity: O(n)

- Whole tree traversal is O(n)
- Each traversal step doesn't have any additional nested loop, thus maintain O(1)
on each node calculation

Space complexity: O(n)

- Used to store Adj array and trace result array

```python
def debug(s):
    # print(s)
    pass

class Solution:
    def longestPath(self, parent: List[int], s: str) -> int:
        n = len(s)
        childs = defaultdict(list)

        # Build adj array for better traversal
        for c, p in enumerate(parent):
            if p == -1:
                continue
            childs[p].append(c)

        debug(childs)
        result = 1

        # best_path keep track of the length of best path for each node
        # needed to store and retrive best_path on each node (start from that node down to it child)
        # when doing tree traversal
        best_path = [1] * n

        trace_path = [-1] * n
        def construct_trace_path(node):
            path = []
            n = node
            while n != -1:
                path += [f"{n} (\"{s[n]}\")"]
                n = trace_path[n]
            return path

        def find(node):
            nonlocal result

            # debug(f"[DEBUG] Travesal {node}")
            # greedy connect, 2 longest path which can connect to current node
            # will be choose for the best possible path (which contain this current node)
            # represent the child node that can be connect
            longest = -1
            longest_next = -1
            for c in childs[node]:
                # Recusive tree traversal
                find(c)

                # Can only connect different character node
                if s[c] == s[node]:
                    continue

                # maintain two best path
                # Keep thing within O(1) using if branching. Alternative can use heap push/pop
                if longest == -1:
                    longest = c
                    # debug(f"[DEBUG] {node}: Updated longest = {longest}")
                elif best_path[longest] < best_path[c]:
                    longest_next = longest
                    longest = c
                    # debug(f"[DEBUG] {node}: Updated longest = {longest}, longest_next = {longest_next}")
                elif longest_next == -1 or best_path[longest_next] < best_path[c]:
                    longest_next = c
                    # debug(f"[DEBUG] {node}: Updated longest_next = {longest_next}")
            
            best_path[node] = 1

            # Have at least one child then it can connect
            # Greedy and form the longest path with current node is start node and connect to
            # longest path child
            if longest != -1:
                best_path[node] += best_path[longest]
                trace_path[node] = longest
            
            # This just check and update result with current can be formed path 
            if longest_next != -1:
                if result < best_path[node] + best_path[longest_next]:
                    result = best_path[node] + best_path[longest_next]
                    # debug(f"[DEBUG] result = {result}, path = {construct_trace_path(node)}")
                    # debug(f"[DEBUG] + merged path = {construct_trace_path(longest_next)}")
            else:
                if result < best_path[node]:
                    result = best_path[node]
                    # debug(f"[DEBUG] result = {result}, path = {construct_trace_path(node)}")
            # debug(f"[DEBUG] Done {node}")
        find(0)

        return result
```
