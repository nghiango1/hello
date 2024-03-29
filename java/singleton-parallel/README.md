# Singleton

Who isn't hate this patern

## The problem

You have only one object of this class avaiable for entire runtime. Until use want to make it lazy created and every other function start a race condition to create the object.

- Better Init it in a single thread then
- Block everything until a thread finish changing Singleton object

But Singleton is the way to help with memory management, why?
- If you not care about memory just please using all static property instead, it is way safer and done the same thing.
- By using singleton, we make sure that if it is the case that we don't need the object, only some static properties contain pointer is init and use in the system, instead a whole big chunk of memory already alocated at the start of the progam.

So Init it in a single thread at start of the program should not be the case. But after start program init, creating Singleton object in only one thread is easier said than done in multi-thread progam.

This is a strict case that we want to do Singletion - Parallel - Lazy instance initiation

## What this aim for

Java tool chain is really mature, so I want to know what Java developer environment give to developer to debug a progam
- DAP
- LSP

## Implement

### Problematic code

A example Singleton with counter on 3 thread output that without thread lock already create 2 instance

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

You could make it even worst by assigning `_instance = new Singleton()` instead of using temporary `obj` object, which make multinstance just stack on each other. Then after that, thread access `_instance` value which were still being initiliazed by multi thread. And hope god know what the current instance data will be. Anyway, here is the result:
- There is two instance was created (I used 0 - index `instanceID`, the instance id showed up have id 1)
- `data` value is all over the place (different than 8000), this is because that `_instance` change to object id 1 mid initialization, causing some addition from object id 0 initialization make it through and affect the value of object id 1 `data`
- The process take that access Singleton instance is fast enough that the `"Lazy create..."` log string fail to log the createtion of object id 1

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

Even with larger among of thread, `"Lazy create ..."` log string only appear once and all thread start to access data after `"Finish create object ..."` log string appear

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

That pretty much cover the whole point of this project. Try to use your debug environment (DAP/Jetbean/...) that detect all what I said
