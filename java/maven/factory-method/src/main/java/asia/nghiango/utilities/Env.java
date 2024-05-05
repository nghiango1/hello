package asia.nghiango.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.System.Logger.Level;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import asia.nghiango.dbhelper.DatabaseType;

/**
 * Env handle the program variable that come from external
 */
public class Env {

    private static Dictionary<String, String> env;

    public static String getEnvironmentValue(String key) {
        return env.get(key);
    }

    /**
     * Is verbose return true when the environment variable env or env['VERBOSE']
     * isn't set
     */
    public static Boolean isVerbose() {
        if (env == null)
            return true;

        String isVerbose = env.get("VERBOSE");
        if (isVerbose == null)
            return true;

        return isVerbose.compareTo("TRUE") == 0;
    }

    /**
     * Get wanted database type from enviroment variable, fall back to DatabaseType
     * inmemory datastore if env or env['DATABASE'] isn't set
     *
     * @return
     */
    public static DatabaseType getDatabaseType() {
        if (env == null)
            return DatabaseType.INMEM;

        String databaseTypeString = env.get("DATABASE");
        if (databaseTypeString == null)
            return DatabaseType.INMEM;

        return DatabaseType.convertToEnumValue(databaseTypeString);
    }

    public static java.util.logging.Level getLogLevel() {
        if (isVerbose())
            return java.util.logging.Level.FINE;
        return java.util.logging.Level.SEVERE;
    }

    public static void generateEnvFile() {
        URI fileURI = Env.getEnvFileURI();
        File file = new File(fileURI);
        Writer w;
        try {
            w = new FileWriter(file);
        } catch (IOException e) {
            Log.printLog(Level.ERROR, String.format("Can't access file at %s, abort\n", fileURI.toString()));
            return;
        }

        try {
            Enumeration<String> dkey = env.keys();
            while (dkey.hasMoreElements()) {
                String k = dkey.nextElement();
                String v = env.get(k);

                w.write(String.format("%s=%s\n", k, v));
            }
            w.close();
        } catch (IOException e) {
            Log.printLog(Level.ERROR, String.format("Can't write to file at %s, abort\n", fileURI.toString()));
            return;
        }
    }

    private static URI getDefaultEnvFileURI() {
        String defaultDirectory = System.getenv("HOME") + "/.env";
        URI envFileURI = null;
        try {
            envFileURI = new URI("file", null, defaultDirectory, null);
        } catch (URISyntaxException e) {
            Log.printLog(Level.ERROR,
                    "Default env file URI is wrong, this error should not been happend, check the source code!");
        }

        return envFileURI;
    }

    /**
     * Check if DEMO_ENV_PATH is create yet, if not return default value for .env
     * file
     *
     * @return URI to access env file
     */
    private static URI getEnvFileURI() {
        String path = System.getenv("DEMO_ENV_PATH");
        URI def = null;
        if (path == null) {
            return getDefaultEnvFileURI();
        }

        try {
            def = new URI(path);
        } catch (URISyntaxException e) {
            Log.printLog(Level.ERROR, "OS env DEMO_ENV_PATH file URI is wrong");
            return getDefaultEnvFileURI();
        }

        return def;
    }

    public static void readEnv() {
        File f = new File(getEnvFileURI());
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            Log.printLog(Level.WARNING, "Can't find .env file, all value set to default");
            setDefault();
            generateEnvFile();
            return;
        }

        Log.printLog(Level.DEBUG, "Environment variable setup via File at " + f.toURI().toString());
        env = new Hashtable<String, String>();
        while (s.hasNextLine()) {
            String data = s.nextLine();
            Log.printLog(Level.INFO, data);

            if (data.charAt(0) == '#') {
                // Assuming this is a comment line
                continue;
            }
            String[] line = data.split("=", 2);
            if (!validateEnvLine(line))
                continue;

            String key = line[0];
            String value = line[1];
            Env.env.put(key, value);
        }

        s.close();
    }

    private static Boolean validateEnvLine(String[] line) {
        if (line.length != 2) {
            Log.printLog(Level.ERROR,
                    "Validate fail, line can't have more than 2 equal sign. Skipping line: \"" + line + "\"");
            return false;
        }

        return true;
    }

    private static void setDefault() {
        env = new Hashtable<String, String>();
        env.put("ENVIRONMENT", "DEV");
        env.put("VERBOSE", "TRUE");
        env.put("DATABASE", "INMEM");
        env.put("MYSQL_CONNECTION_STRING", "jdbc:mysql://localhost:3306/webstat?user=root&password=example");
        env.put("POSTGRESQL_CONNECTION_STRING", "jdbc:mysql://localhost:3306/webstat?user=root&password=example");
    }
}
