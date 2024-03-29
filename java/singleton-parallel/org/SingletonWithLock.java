package org;

/**
 * Singleton is the way to help with memory management, why? If you not care
 * about memory just please using all static property instead, it is way safer
 * and done the same thing. By using singleton, we make sure that if we didn't
 * need the object, only some static properties contain pointer is init and use
 * in the system, instead a whole big chunk of memory already alocated at the
 * start of the progam.
 */
public class SingletonWithLock{
    private static boolean isLock = false;
    private static SingletonWithLock _instance = null;
    private static int _totalInstance = 0;
    private int instanceID = 0;
    private int data = 8000;

    public static int getTotalInstance() {
        return SingletonWithLock._totalInstance;
    }

    private SingletonWithLock() {
        this.data = 8000;
        this.instanceID = _totalInstance;
        _totalInstance += 1;
    }

    public static SingletonWithLock getInstance() {
        if (_instance == null && !isLock) {
            isLock = true;
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

        return SingletonWithLock._instance;
    }

    public String toString() {
        return String.format("This is instance id %d, with hash %d, which containing %d",
                this.instanceID, this.hashCode(), this.data);
    }
}
