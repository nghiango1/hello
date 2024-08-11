from typing import Dict, List
import gc


MODULE = 10**9 + 7


class Group:
    def __init__(self, member: List[int], block: List[int], id: int):
        self.id = id
        self.member = member
        self.block = block


class Tree:
    def __init__(self, totalNode):
        # Input from the problem
        self.totalNode = totalNode
        self.adj: Dict[int, List[int]] = {}
        self.special: List[int] = [False] * totalNode

        # Calculate pre count within O(n)
        self.preCount: List[int] = [-1] * totalNode

        # Store relevant infomation
        self.groupId: List[int] = [-1] * totalNode
        self.currGroupId = -1
        self.group: List[Group] = []

    def addEdge(self, u: int, v: int):
        u = u - 1
        v = v - 1
        if u not in self.adj:
            self.adj[u] = []
        if v not in self.adj:
            self.adj[v] = []
        self.adj[u].append(v)
        self.adj[v].append(u)

    def selfTraversal(self, start=0):
        parent = [-1] * self.totalNode
        stack = [start]
        while stack.__len__() > 0:
            curr = stack.pop()
            for next in self.adj[curr]:
                if next == parent[curr]:
                    continue
                parent[next] = curr
                stack.append(next)

    def initPreCount(self):
        # Traversal all node in the tree, generating group of non special node
        # Should just cost O(n)
        for i in range(self.totalNode):
            if self.special[i]:
                continue
            if self.groupId[i] == -1:
                self.nonSpecialTraversal(i)

        # This is a bit tricky, to calculate each one, we have to traversal
        # each node and it's adj once, which can cause this to become O(n**2)
        for i in range(self.totalNode):
            if self.special[i]:
                self.preCount[i] = self.calSpecial(i)
            else:
                self.preCount[i] = self.calNonSpecial(i)

    def calSpecial(self, start):
        # Take it self
        res = 1

        # And every group around it, could cost O(n)? but it quite minimal
        for next in self.adj[start]:
            if not self.special[next]:
                res += self.group[self.groupId[next]].member.__len__()
        return res

    def calNonSpecial(self, start):
        startGroup = self.group[self.groupId[start]]

        # Take all the block around it
        res = startGroup.block.__len__()

        # Then traversal to closest group connected to it. Could cost O(n**2)
        # But it also quite minimal
        for closeSpecialNode in startGroup.block:
            for closeNode in self.adj[closeSpecialNode]:
                if self.special[closeNode]:
                    continue
                if self.groupId[closeNode] == startGroup.id:
                    continue
                closeNodeGroup = self.group[self.groupId[closeNode]]
                res += closeNodeGroup.member.__len__()
        return res

    # DFS to find all non special group
    def nonSpecialTraversal(self, start):
        # temp group info
        member = [start]
        block = []

        # DFS tree traversal
        stack = [start]
        parent = [-1] * self.totalNode
        while stack.__len__() > 0:
            curr = stack.pop()
            for next in self.adj[curr]:
                if next == parent[curr]:
                    continue

                parent[next] = curr
                if not self.special[next]:
                    # Non specical node is a member of the group
                    member.append(next)
                    stack.append(next)
                else:
                    # Specical nodes is the border of the group
                    block.append(next)

        # Storing group
        self.currGroupId += 1
        self.group.append(Group(member, block, self.currGroupId))

        # Update groupId for each member, needed for calculating later
        for curr in member:
            self.groupId[curr] = self.currGroupId

    def clear(self):
        self.adj.clear()
        self.special.clear()
        self.preCount.clear()
        self.group.clear()

    def __str__(self):
        res = "{ "
        res += f"adj = {self.adj}"
        res += f", special = {self.special}"
        res += f", totalNode = {self.totalNode}"
        res += f", preCount = {self.preCount}"
        res += " }"
        return res

    def dump(self):
        print(self.__str__())


treeA: Tree
treeB: Tree


def readinput():
    global treeA, treeB

    temps = input().split()
    n_a, m_a = int(temps[0]), int(temps[1])
    treeA = Tree(n_a)

    temps = input().split()
    for i in temps:
        treeA.special[int(i) - 1] = True

    for _ in range(n_a - 1):
        temps = input().split()
        u, v = int(temps[0]), int(temps[1])
        treeA.addEdge(u, v)

    temps = input().split()
    n_b, m_b = int(temps[0]), int(temps[1])
    treeB = Tree(n_b)

    temps = input().split()
    for i in temps:
        treeB.special[int(i) - 1] = True

    for _ in range(n_b - 1):
        temps = input().split()
        u, v = int(temps[0]), int(temps[1])
        treeB.addEdge(u, v)


def dumpInput():
    global treeA, treeB
    treeA.dump()
    treeB.dump()


def solve():
    global treeA, treeB, MODULE
    treeA.initPreCount()
    treeB.initPreCount()

    # Try to do it in O(n)
    res = 0
    temp1 = 0
    for i in range(treeB.totalNode):
        temp1 += treeB.preCount[i]
        temp1 %= MODULE

    temp2 = 0
    for i in range(treeB.totalNode):
        temp2 += (i+1) * treeB.preCount[i]
        temp2 %= MODULE

    for i in range(treeA.totalNode):
        res += treeA.preCount[i] * (temp2 + (i+1)*temp1) % MODULE
        res %= MODULE

    print(res)


def test(example=-1):
    global treeA, treeB, MODULE
    if example < 0 or example == 1:
        treeA = Tree(2)
        treeA.adj = {0: [1], 1: [0]}
        special = {0, 1}
        for i in special:
            treeA.special[i] = True

        treeB = Tree(2)
        treeB.adj = {1: [0], 0: [1]}
        special = {0, 1}
        for i in special:
            treeB.special[i] = True
        solve()

    if example < 0 or example == 2:
        treeA = Tree(3)
        treeA.adj = {0: [1], 1: [0, 2], 2: [1]}
        special = {0, 1, 2}
        for i in special:
            treeA.special[i] = True

        treeB = Tree(3)
        treeB.adj = {0: [2], 2: [0, 1], 1: [2]}
        special = {1}
        for i in special:
            treeB.special[i] = True
        solve()

    if example < 0 or example == 4:
        treeA = Tree(8)
        treeA.adj = {1: [0], 0: [1, 2, 3, 4, 5, 6, 7], 2: [
            0], 3: [0], 4: [0], 5: [0], 6: [0], 7: [0]}
        special = set([0, 2, 4, 6])
        for i in special:
            treeA.special[i] = True

        treeB = Tree(4)
        treeB.adj = {0: [1, 3], 1: [0], 3: [0, 2], 2: [3]}
        special = set([1, 2])
        for i in special:
            treeB.special[i] = True
        solve()


def main():
    global treeA, treeB, piorityQueue

    c = int(input())
    for _ in range(c):
        gc.collect()
        readinput()
        # dumpInput()
        solve()
        treeA.clear()
        treeB.clear()


if __name__ == "__main__":
    test()
    # main()
