public class primitives {
    public static void main(String[] args) {
        boolean bool = true;

        // 1 bytes
        byte n = Byte.MAX_VALUE;
        // 2 bytes
        short n2 = Short.MAX_VALUE;
        // 4 bytes
        int n3 = Integer.MAX_VALUE;
        // 8 bytes
        long n4 = Long.MAX_VALUE;
        // To escape % in format string. We do %%
        System.out.printf(
                "Here I using %%x to show bytes value to make it easier to comparing each number\nn = %x\nn2 = %x\nn3 = %x\nn4 = %x\nbool = %b\n",
                n, n2, n3, n4, bool);

        // char? isn't it bytes in c? well no, java support utf-16 character. So, some 4 bytes character will need to be break down to haft length
        // Here is a 2 bytes length vietnamses character
        char c = 'ế';

        // Here emoji is a 4 bytes character, which mean the start of log command `c == ...` is 3 bytes ahead
        String example = "😁 c = %c = %d = %x\n";

        System.out.printf(example.substring(3, example.length()), c, (int) c, (int) c);

        // Let get our emoji
        char firstHaft = example.charAt(0);
        char secondHaft = example.charAt(1);
        System.out.printf(example.substring(3, example.length()), firstHaft, (int) firstHaft, (int) firstHaft);
        System.out.printf(example.substring(3, example.length()), secondHaft, (int) secondHaft, (int) secondHaft);

        // Array in java
        char[] concat = {firstHaft, secondHaft};
        System.out.printf("%s have length %d\n", String.valueOf(concat), concat.length);

        // These is no primitives fixed point
        // Both float (32bit) and double (64bit) are floating point number
        float f = 0.1f;
        double d = 0.1f;
        System.out.println(String.format("f = %.2f and d = %.2f", f, d));
    }
}
