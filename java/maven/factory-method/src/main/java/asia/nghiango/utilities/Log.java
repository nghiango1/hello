package asia.nghiango.utilities;

import java.lang.System.Logger.Level;

/**
 * Log
 */
public class Log {

    private class Style {
        public static String DEBUG = "\033[94m";
        public static String CYAN = "\033[96m";
        public static String GREEN = "\033[92m";
        public static String WARNING = "\033[93m";
        public static String FAIL = "\033[91m";
        public static String BOLD = "\033[1m";
        public static String UNDERLINE = "\033[4m";
        public static String RESET = "\033[0m";
    }

    public static java.util.logging.Level getLogLevel() {
        if (checkVerbose())
            return java.util.logging.Level.FINE;
        return java.util.logging.Level.SEVERE;
    }

    /**
     * Is verbose return true when the environment variable env or env['VERBOSE']
     * isn't set, this send
     */
    public static Boolean checkVerbose() {
        if (Env.getVerbose().isEmpty()) {
            Log._safePrintLog(Level.INFO, "Environment env doesn't set yet, checkVerbose() fail back to true");
            return true;
        }

        return Env.getVerbose().get();
    }

    public static void printLog(Level level, String logString) {
        _printLog(level, logString, true);
    }

    public static void printLog(Level level, String logString, Boolean color) {
        _printLog(level, logString, color);
    }

    /**
     * Safe print log method come to writing Log inside of this Log module, this is
     * here to ensure we don't start a dead lock method call when accessing Log.
     *
     * This method should not calling any other method that may cause dead
     * lock/infinity loop.
     *
     * @param level
     * @param logString
     */
    private static void _safePrintLog(Level level, String logString) {
        String noNewline = logString.replace("\n", "\\n");
        System.out.printf("[%s] (safe): %s\n", level, noNewline);
    }

    private static void _printLog(Level level, String logString, Boolean color) {
        if (level.getSeverity() < getLogLevel().intValue())
            return;

        if (color)
            System.out.printf(styleLog(level, logString));
        else
            System.out.printf(noStyleLog(level, logString));
    }

    private static String noStyleLog(Level level, String logString) {
        String noNewline = logString.replace("\n", "\\n");
        return String.format("[%s]: %s\n", level, noNewline);
    }

    private static String styleLog(Level level, String logString) {
        String mainColor = "";
        switch (level) {
            case ERROR:
                mainColor = Style.FAIL;
                break;

            case DEBUG:
                mainColor = Style.DEBUG;
                break;

            default:
                break;
        }

        String noNewline = logString.replace("\n", Style.BOLD + "\\n" + Style.RESET + mainColor);
        return String.format("%s[%s]: %s%s\n", mainColor, level, noNewline, Style.RESET);
    }
}
