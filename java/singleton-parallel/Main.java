import java.util.List;
import java.util.ArrayList;

import org.*;

/**
 * Main function using thread
 */
public class Main extends Thread {
    private static boolean look = false;
    private static int threadCount = 0;
    private int threadID = 0;

    private Main() {
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
        Main.threadCount += 1;
        look = false;
    }

    public void run() {
        System.out.printf("Thread %d is running\n", this.threadID);
        SingletonWithLock s = SingletonWithLock.getInstance();
        System.out.printf("Hello from thread %d! %s\n", this.threadID, s.toString());
    }

    public static void main(String[] args) {
        List<Main> threadList = new ArrayList<Main>();

        for (int i = 0; i < 13; i++) {
            Main temp = new Main();
            temp.start();
            threadList.add(temp);
        }

        while (true) {
            boolean isDoneFlag = true;
            for (Main childThread : threadList) {
                if (childThread.isAlive()) {
                    isDoneFlag = false;
                };
            };
            if (isDoneFlag) break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
