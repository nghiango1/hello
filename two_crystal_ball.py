import math


class Problem():
    def __init__(self, n, b):
        self.n = n
        self.b = b
        self.count = 2
        self.check = 1

    def checkBreak(self, x):
        if x >= self.b:
            assert self.count > 0
            self.count -= 1
        return x >= self.b

    def checkResult(self, x):
        assert x == self.b
        return x == self.b


def solve(n, cb, cr):
    step = math.trunc(math.sqrt(n))
    curr = 0
    for i in range(0, n, step):
        if cb(i):
            break
        curr = i
    for i in range(curr, n):
        if cb(i):
            if not cr(i):
                break
            return i
    return -1


def test():
    test = [(55, 14), (422, 12), (441, 255), (245, 112), (12421441, 244124)]
    for n, b in test:
        problem = Problem(n, b)
        cb = problem.checkBreak
        cr = problem.checkResult
        res = solve(n, cb, cr)
        assert cr(res)

    print("Done")


if __name__ == "__main__":
    test()
