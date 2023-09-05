import java.util.ArrayList;
import java.util.LinkedList;
import java.time.Duration;
import java.util.Random;

public class TestLinkedList {
    public static void main(String[] args) {
        // LinkedList<Integer> List = new LinkedList<>();
        ArrayList<Integer> List = new ArrayList<>();

        int multi = 10000;
        int length = 100 * multi;
        int total = 5 * multi;
        int first = 50 * multi;

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(1000000);
            List.add(randomNumber);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            int randomNumber = random.nextInt(first);
            List.remove(randomNumber);
            if (i % 1000 == 0)
                System.out.println(i / 1000);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        Duration duration = Duration.ofMillis(elapsedTime);

        System.out.println("Time taken: " + formatDuration(duration));

    }

    public static String formatDuration(Duration duration) {
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long milliseconds = duration.toMillisPart();

        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }
}
