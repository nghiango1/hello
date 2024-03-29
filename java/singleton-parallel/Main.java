import org.Singleton;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Singleton s = Singleton.getInstance();
        System.out.printf("Hello %s\n", s.toString());
        System.out.printf("World\n");
    }
}
