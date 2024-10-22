import time
import threading


class Log:
    lock = threading.Lock()
    i = 0

    @staticmethod
    def increase(msg):
        with Log.lock:
            Log.i += 1
            print(msg + ": Increase i=" + str(Log.i) + "\n")
            # print(Log.i)

    @staticmethod
    def get(msg):
        with Log.lock:
            # print(msg + ": Get i=" + str(Log.i) + "\n")
            return Log.i


def thread1():
    while (Log.get("thread1") < 10):
        Log.increase("thread1")
        time.sleep(0.1)


def thread2():
    while (Log.get("thread2") < 10):
        Log.increase("thread2")
        time.sleep(0.1)


threading.Thread(target=thread1).start()
threading.Thread(target=thread2).start()
