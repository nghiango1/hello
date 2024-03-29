package org;

/**
 * Singleton
 */
public class Singleton {
    private static Singleton instance = null;
    private static int totalInstance = 0;
    private int instanceID = 0;

    public static int getTotalInstance() {
        return Singleton.totalInstance;
    }

    private Singleton() {
        Singleton.totalInstance += 1;
        this.instanceID += 1;
    }

    public static Singleton getInstance() {
        if (Singleton.instance == null) {
            Singleton.instance = new Singleton();
        }

        return Singleton.instance;
    } 

    public String toString(){
        return String.format("This is instance id %d!", this.instanceID);
    }

}
