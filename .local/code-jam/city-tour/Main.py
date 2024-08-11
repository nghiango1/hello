from heapq import heappop, heappush
import gc

n = 0
d = [[[0]*10 for _ in range(10)] for _ in range(10)]
best = {}
piorityQueue = []


def setMark(mark: int, x: int):
    return mark | (1 << x)


def getMark(mark: int, x: int):
    return (mark & (1 << x)) >> x


def dumpMark(mark: int, n: int):
    if mark == 0:
        return "0" * n
    return "0" * (n - mark.bit_length()) + bin(mark)[2:]


class State:
    def __init__(self, city=-1, cityMark=0, transportMark=0):
        self.city = city
        self.cityMark = cityMark
        self.transportMark = transportMark

    def __hash__(self):
        return hash((self.city, self.cityMark, self.transportMark))


class QueueData:
    def __init__(self, length=0, city=-1, cityMark=0, transportMark=0):
        self.length = length
        self.state = State(city, cityMark, transportMark)

    def visistedCity(self, city: int):
        return getMark(self.state.cityMark, city) == 1

    def usedTransport(self, transport: int):
        return getMark(self.state.transportMark, transport) == 1

    def getState(self):
        return self.state

    def __eq__(self, x):
        # print(self.__str__(), "==",  x.__str__())
        return self.length == x.length

    def __lt__(self, x):
        # print(self.__str__(), "<", x.__str__())
        return self.length < x.length

    def __le__(self, x):
        # print(self.__str__(), "<=", x.__str__())
        return self.length <= x.length

    def __str__(self):
        return f"<length = {self.length}, city = {self.state.city}, cityMark = {self.state.cityMark}, transpostMark = {self.state.transportMark}>"


def readinput():
    global n, d

    n = int(input())
    curr = 0
    for _ in range(0, n**2, n):
        for i in range(n):
            temps = input().split()
            for j, value in enumerate(temps):
                d[curr][i][j] = int(value)
        curr += 1


def solveMax() -> int:
    global n, d, piorityQueue, best

    maxPath = 0

    # 1st city
    heappush(piorityQueue, QueueData(
        length=0, city=0, cityMark=0, transportMark=0))

    while len(piorityQueue) > 0:
        curr: QueueData = heappop(piorityQueue)
        # print(f"curr = {curr.__str__()}")

        if curr.state.__hash__() in best and (curr.length > best[curr.state.__hash__()]):
            continue

        done = True
        for nextCity in range(1, n):
            if curr.visistedCity(nextCity):
                continue

            done = False
            nextCityMark = setMark(curr.state.cityMark, nextCity)

            for nextTransport in range(n):
                if curr.usedTransport(nextTransport):
                    continue

                nextTransportMark = setMark(
                    curr.state.transportMark, nextTransport)

                # No path check
                if d[nextTransport][curr.state.city][nextCity] == 0:
                    continue

                nextLength = curr.length - \
                    d[nextTransport][curr.state.city][nextCity]

                next = QueueData(length=nextLength,
                                 city=nextCity,
                                 cityMark=nextCityMark,
                                 transportMark=nextTransportMark)

                if next.state.__hash__() not in best or best[next.state.__hash__()] > next.length:
                    best[next.state.__hash__()] = next.length
                    heappush(piorityQueue, next)

        # Done and goes back to city 1 (0 index)
        if done and curr.visistedCity(0) == 0:
            nextCity = 0
            nextTransport = -1

            for nextTransport in range(n):
                if curr.usedTransport(nextTransport) == 1:
                    continue

                break

            # No path check
            if d[nextTransport][curr.state.city][nextCity] == 0:
                continue

            if nextTransport == -1:
                # print("NO WAY")
                continue

            nextLength = curr.length - \
                d[nextTransport][curr.state.city][nextCity]

            if maxPath < -nextLength:
                maxPath = -nextLength

    best.clear()
    return maxPath


def solveMin() -> int:
    global n, d, piorityQueue

    minPath = None

    # 1st city
    heappush(piorityQueue, QueueData(
        length=0, city=0, cityMark=0, transportMark=0))

    while len(piorityQueue) > 0:
        curr: QueueData = heappop(piorityQueue)
        # print(f"curr = {curr.__str__()}")
        if minPath is not None and minPath <= curr.length:
            piorityQueue.clear()
            break

        done = True
        for nextCity in range(1, n):
            if curr.visistedCity(nextCity):
                continue

            done = False
            nextCityMark = setMark(curr.state.cityMark, nextCity)

            for nextTransport in range(n):
                if curr.usedTransport(nextTransport):
                    continue

                nextTransportMark = setMark(
                    curr.state.transportMark, nextTransport)

                # No path check
                if d[nextTransport][curr.state.city][nextCity] == 0:
                    continue

                nextLength = curr.length + \
                    d[nextTransport][curr.state.city][nextCity]

                heappush(piorityQueue, QueueData(length=nextLength,
                                                 city=nextCity,
                                                 cityMark=nextCityMark,
                                                 transportMark=nextTransportMark))

        # Done and goes back to city 1 (0 index)
        if done and curr.visistedCity(0) == 0:
            nextCity = 0
            nextTransport = -1

            for nextTransport in range(n):
                if curr.usedTransport(nextTransport) == 1:
                    continue

                break

            # No path check
            if d[nextTransport][curr.state.city][nextCity] == 0:
                continue

            if nextTransport == -1:
                # print("NO WAY")
                continue

            nextLength = curr.length + \
                d[nextTransport][curr.state.city][nextCity]

            if minPath is None:
                minPath = nextLength
            if minPath > nextLength:
                minPath = nextLength

    if minPath is None:
        return 0

    return minPath


def dumpInput():
    global n, d
    print(f"n = {n}")
    for k in range(n):
        print(f"transpost {k}:")
        for i in range(n):
            for j in range(n):
                print(d[k][i][j], end=" ")
            print()


def test():
    global n, d
    n = 4
    d = [[[0, 1, 2, 3], [1, 0, 4, 5], [2, 4, 0, 6], [3, 5, 6, 0]], [[0, 2, 3, 5], [2, 0, 7, 11], [3, 7, 0, 13], [5, 11, 13, 0]], [
        [0, 1, 3, 3], [1, 0, 7, 0], [3, 7, 0, 0], [3, 0, 0, 0]], [[0, 0, 3, 5], [0, 0, 7, 9], [3, 7, 0, 1], [5, 9, 1, 0]]]
    print(solveMin())
    print(solveMax())


def main():
    global piorityQueue

    t = int(input())
    for _ in range(t):
        gc.collect()
        readinput()
        # dumpInput()
        # print(f"n = {n}, d = {d}")
        minPath = solveMin()
        maxPath = solveMax()
        print(minPath, maxPath)


if __name__ == "__main__":
    # test()
    main()
