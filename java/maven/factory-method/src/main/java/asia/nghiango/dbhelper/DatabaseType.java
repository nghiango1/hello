package asia.nghiango.dbhelper;

import java.lang.System.Logger.Level;

import asia.nghiango.utilities.Log;

/**
 * Supported database that can be handler by the current project, can be feed to
 * {@link DatabaseHandlerFactory} when creating a new {@link DatabaseHandler}
 * interface object
 */
public enum DatabaseType {
    INMEM("INMEM"),
    MYSQL("MYSQL"),
    POSTGRESQL("POSTGRESQL"),
    FILE("FILE");

    /**
     * Converte a string to DatabaseType, if string isn't coresponse to a support
     * database type that have support and can be handle then this fall back to In
     * memory datastore
     *
     * @param stringType
     */
    public static DatabaseType convertToEnumValue(String stringType) {
        switch (stringType) {
            case "INMEM":
                return DatabaseType.INMEM;

            case "MYSQL":
                return DatabaseType.MYSQL;

            case "POSTGRESQL":
                return DatabaseType.POSTGRESQL;

            default:
                Log.printLog(Level.WARNING,
                        "The DATABASE environment have unsupported type string, fall back to `INMEM` in-memory datastore");
                return DatabaseType.INMEM;
        }
    }

    private final String databaseType;

    private DatabaseType(final String databaseType) {
        this.databaseType = databaseType;
    }

    public String convertToString() {
        return this.databaseType;

    }
}
