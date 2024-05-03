package asia.nghiango.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Env
 */
public class Env {

    private static Dictionary<String, String> env;

    public static String getEnvironmentValue(String key) {
        return env.get(key);
    }

    public static Boolean isVerbose() {
        String isVerbose = env.get("VERBOSE");
        if (isVerbose == null) {
            setDefault();
            return false;
        } else {
            return !(isVerbose == "FALSE");
        }
    }

    public static Level getLogLevel() {
        if (isVerbose())
            return Level.FINE;
        return Level.SEVERE;
    }

    public static void readEnv() {
        Env.setDefault();
        File f = new File(".env");
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find .env file, all value set to default");
            return;
        }

        while (s.hasNextLine()) {
            String data = s.nextLine();
            if (data.charAt(0) == '#') {
                // Assuming this is a comment line
                continue;
            }
            String[] line = data.split("=");
            if (!validateEnvLine(line)) {
                continue;
            }

            String key = line[0];
            String value = line[1];
            Env.env.put(key, value);
        }

        s.close();
    }

    private static Boolean validateEnvLine(String[] line) {
        if (line.length != 2) {
            System.out.println("Validate fail, line can't have more than 2 equal sign!");
            return false;
        }

        return true;
    }

    private static void setDefault() {
        env = new Hashtable<String, String>();
        env.put("VERBOSE", "TRUE");
    }
}
