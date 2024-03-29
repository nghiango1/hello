# Singleton

Who isn't hate this patern

## The problem

You have only one object of this class avaiable for entire program runtime. Until you want to make it to be lazy initializtion and every other functions start a race condition to create the object. And you leave with two option:

- Better initializing it in a single thread then (At a start of program, or whatever...)
- Block everything until the first thread finish initializing/changing Singleton object

But Singleton is the way to help with memory management, why?

- If you not care about memory, just please using all static property instead, it is way safer and done the same job.
- By using singleton, we make sure that if it is the case that we don't need the object, only some static properties contain pointer is alocated, instead a whole big chunk of all possible data memory (which we won't use!) at the start of the progam.

So initializating Singleton in a single thread at start of the program should not be the use case for Singleton. But after program has start and multiple thread start rolling, creating a Singleton object in only one thread and keep all other running is easier said than done. This is a strict case that we want to do Singletion - Parallel - Lazy instance initialization

> Also, we may want to modify our Singleton data after that, but I'm not touching that here.

## What this aim for

Java tool chain is really mature, so I want to know what Java development tools/environment give to developer to debug a multi thread progam (which I not have that big of hope here)

## Implement

### The problematic code

A example Singleton without thread lock: on 3 thread, output said that we already create 3 instance (from `"Lazy create..."` log string), and two of them have the same ID.

```
Thread 0 is running
Thread 2 is running
Thread 1 is running
Lazy create object id 0, with hash 623578683 start ...
Lazy create object id 0, with hash 1730871013 start ...
Lazy create object id 1, with hash 964752528 start ...
Finish create object id 0
Hello from thread 2! This is instance id 0, with hash 623578683, which containing 8000
Finish create object id 0
Finish create object id 1
Hello from thread 0! This is instance id 0, with hash 1730871013, which containing 8000
Hello from thread 1! This is instance id 1, with hash 964752528, which containing 8000
```

Example code:

```java
public class Singleton {
    private static Singleton _instance = null;
    private static int _totalInstance = 0;
    private int instanceID = 0;
    private int data = 8000;

    private Singleton() {
        this.data = 8000;
        this.instanceID = _totalInstance;
        _totalInstance += 1;
    }

    public static Singleton getInstance() {
        if (_instance == null) {
            Singleton obj = new Singleton();

            System.out.printf("Lazy create object id %d, with hash %d start ...\n", obj.instanceID,
                    obj.hashCode());

            // Assumming that the initlization is really hard and time consumming
            for (int i = 1; i < 500_000_000; i++) {
                obj.data += i;
                obj.data -= i;
            }

            System.out.printf("Finish create object id %d\n", obj.instanceID);
            _instance = obj;
        }

        return Singleton._instance;
    }

    public String toString() {
        return String.format("This is instance id %d, with hash %d, which containing %d",
                this.instanceID, this.hashCode(), this.data);
    }
}
```

You could make it even worst by assigning `_instance = new Singleton()` instead of using temporary `obj` object, which make multi instance initlization just stack on each other. Then right after that, the first thread access `_instance` value which were still being initialized by others thread. And you should hope and pray to know what the current `_instance` data will be. Anyway, here is the result:

- There is two instance was created from our `"Lazy create.."` log string (I used 0 - index `instanceID`, the instance id showed up have id 1 which is a bit better)
- `data` value is all over the place (different than 8000), this is because that `_instance` reference object have been change to `obj` with id 1 mid initialization, causing some addition from object `obj` id 0 initialization make it through and affect the value of object id 1 `data`
- The runtime access to Singleton instance data is fast enough that even the `"Lazy create..."` log string fail to log the creation of object id 0

```
Thread 0 is running
Thread 2 is running
Thread 1 is running
Lazy create object id 1, with hash 1730871013 start ...
Lazy create object id 1, with hash 1730871013 start ...
Hello from thread 1! This is instance id 1, with hash 1730871013, which containing 8000
Finish create object id 1
Hello from thread 0! This is instance id 1, with hash 1730871013, which containing 1414398
Finish create object id 1
Hello from thread 2! This is instance id 1, with hash 1730871013, which containing 1414398
```

Code example:
```java
public class Singleton {
    private static Singleton _instance = null;
    private static int _totalInstance = 0;
    private int instanceID = 0;
    private int data = 8000;

    private Singleton() {
        this.data = 8000;
        this.instanceID = _totalInstance;
        _totalInstance += 1;
    }

    public static Singleton getInstance() {
        if (_instance == null) {
            // Good luck debug this code
            _instance = new Singleton();

            System.out.printf("Lazy create object id %d, with hash %d start ...\n", _instance.instanceID,
                    _instance.hashCode());

            // Assumming that the initlization is really hard and time consumming
            for (int i = 1; i < 500_000_000; i++) {
                _instance.data += i;
                _instance.data -= i;
            }

            System.out.printf("Finish create object id %d\n", _instance.instanceID);
        }

        return Singleton._instance;
    }

    public String toString() {
        return String.format("This is instance id %d, with hash %d, which containing %d",
                this.instanceID, this.hashCode(), this.data);
    }
}
```

### Solution

Here is the better one that have lock implemented

```java
package org;

public class SingletonWithLock{
    private static boolean isLock = false;

    private static SingletonWithLock _instance = null;
    private static int _totalInstance = 0;
    private int instanceID = 0;
    private int data = 8000;

    private SingletonWithLock() {
        this.data = 8000;
        this.instanceID = _totalInstance;
        _totalInstance += 1;
    }

    public static SingletonWithLock getInstance() {
        if (_instance == null && !isLock) {
            isLock = true;
            // Even we didn't use temporary variable, it still fine with lock implemented
            _instance = new SingletonWithLock();

            System.out.printf("Lazy create object id %d, with hash %d start ...\n", _instance.instanceID,
                    _instance.hashCode());

            // Assumming that the initlization is really hard and time consumming
            for (int i = 1; i < 500_000_000; i++) {
                _instance.data += i;
                _instance.data -= i;
            }

            System.out.printf("Finish create object id %d\n", _instance.instanceID);
            isLock = false;
        }

        // We know for sure that if it locked, it being initilization
        while (isLock) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // After above code, _instance have ben properly initilization
        return SingletonWithLock._instance;
    }

    public String toString() {
        return String.format("This is instance id %d, with hash %d, which containing %d",
                this.instanceID, this.hashCode(), this.data);
    }
}
```

Output look great

```
Thread 0 is running
Thread 2 is running
Thread 1 is running
Lazy create object id 0, with hash 1730871013 start ...
Finish create object id 0
Hello from thread 0! This is instance id 0, with hash 1730871013, which containing 8000
Hello from thread 1! This is instance id 0, with hash 1730871013, which containing 8000
Hello from thread 2! This is instance id 0, with hash 1730871013, which containing 8000
```

Even with larger among of thread (13 now instead of 3), `"Lazy create ..."` log string only appear once and all thread start to access data after `"Finish create object ..."` log string appear

```
Thread 0 is running
Thread 12 is running
[...]
Thread 1 is running
Lazy create object id 0, with hash 637978054 start ...
Finish create object id 0
Hello from thread 9! This is instance id 0, with hash 637978054, which containing 8000
Hello from thread 0! This is instance id 0, with hash 637978054, which containing 8000
[...]
Hello from thread 1! This is instance id 0, with hash 637978054, which containing 8000
```

That pretty much cover the whole point of this project. Try to use your debug environment (DAP/Jetbean/...) that detect what I said. But to be honest, most Debug can't do well with multi Thread program at all so I will most likely using some simple `printf`. Nevertheless, good luck on your journey.
