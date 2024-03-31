import java.util.List;
import java.util.ArrayList;

import org.*;

/**
 * Main function using thread
 */
public class Main extends Thread {
    private static boolean look = false;
    private static int threadCount = 0;
    private static int GOOD = 0;
    private static int BAD = 1;

    private int threadID = 0;
    private int type = 1;

    private Main(int type) {
        while (look) {
            System.out.println("Create main locked, wait 100ms");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        look = true;
        this.threadID = Main.threadCount;
        this.type = type;
        Main.threadCount += 1;
        look = false;
    }

    public void goodRun() {
        System.out.printf("Thread %d is running\n", this.threadID);
        SingletonWithLock s = SingletonWithLock.getInstance();
        System.out.printf("Hello from thread %d! %s\n", this.threadID, s.toString());
    }

    public void badRun() {
        System.out.printf("Thread %d is running\n", this.threadID);
        Singleton s = Singleton.getInstance();
        System.out.printf("Hello from thread %d! %s\n", this.threadID, s.toString());
    }

    public void run() {
        if (this.type == BAD) {
            badRun();
        } else {
            goodRun();
        }
    }

    public static void test(int type) {
        List<Main> threadList = new ArrayList<Main>();

        for (int i = 0; i < 10000; i++) {
            Main temp = new Main(type);
            temp.start();
            threadList.add(temp);
        }

        while (true) {
            boolean isDoneFlag = true;
            for (Main childThread : threadList) {
                if (childThread.isAlive()) {
                    isDoneFlag = false;
                }
            }
            if (isDoneFlag)
                break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.printf("Bad Singleton test start\n=====\n");
        test(BAD);
        System.out.printf("Total Singleton instance create: %d\n", Singleton.getTotalInstance());
        System.out.printf("\n\nGood Singleton test start\n=====\n");
        test(GOOD);
        System.out.printf("Total Singleton instance create: %d\n", SingletonWithLock.getTotalInstance());
    }
}
