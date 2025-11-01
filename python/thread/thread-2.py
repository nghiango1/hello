from thread import Log
import time
import threading


def thread1():
    while Log.get("thread1") < 10:
        Log.increase("thread1")
        time.sleep(0.1)


def thread2():
    while Log.get("thread2") < 10:
        Log.increase("thread2")
        time.sleep(0.1)


threading.Thread(target=thread1).start()
threading.Thread(target=thread2).start()
