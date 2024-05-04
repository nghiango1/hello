package asia.nghiango.dbhelper;

import java.util.Optional;

import asia.nghiango.dbhelper.DatabaseHandlerFactory;
import asia.nghiango.utilities.Env;
import asia.nghiango.utilities.Log;

import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DWDFactory, now i actually hate this pattern the moment I have to implement
 * it
 */
public class DatabaseHandlerFactory {

    public static enum DatabaseType {
        INMEM,
        MYSQL,
        POSTGRESQL,
        FILE
    }

    /**
     * This load mysql driver then create a new connection to mysql database
     *
     * @return null if there is any error
     * @return Connection to the databse if connect is success full
     */
    @SuppressWarnings("deprecation")
    public static Optional<Connection> loadMysqlDriver(String connectionString) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.printf("Fail to create new mysql connection, got error: %s\n", ex.toString());
            return Optional.ofNullable(null);
        }

        try {
            Connection conn = DriverManager.getConnection(connectionString);
            return Optional.of(conn);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return Optional.ofNullable(null);
    }

    /**
     * Create a new connection to postgresql database
     * We not need a Driver load for PostgreSQL, this is ensure by the provided jar
     * dependancy
     *
     * @return connection instance for posgres_db
     * @return null if there is any error
     */
    public static Optional<Connection> loadPostgreSQLDriver() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webstat?" +
                    "user=postgres&password=example");
            return Optional.of(conn);
        } catch (SQLException ex) {
            System.out
                    .printf("Fail to create new PostgreSQL connection, get SQLException error: %s\n" + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return Optional.ofNullable(null);
    }

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

    /**
     * A general use factory to create Data Writer Driver with multiple database
     * driver type
     *
     * @param type a enum instant that supported by DWD Factory
     * @return the coresponding DataWriterDriver object base on inputed type
     * @throws throw new UnsupportedOperationException("Unimplemented factory
     *               type");
     */
    public static Optional<DatabaseHandler> create(DatabaseType type) {
        DatabaseHandler dwd;
        Optional<Connection> conn;
        switch (type) {
            case INMEM:
                dwd = new InMemoryDatabaseHandler();
                break;

            case MYSQL:
                conn = loadMysqlDriver(Env.getEnvironmentValue("MYSQL_CONNECTION_STRING"));
                if (conn.isEmpty()) {
                    dwd = null;
                    break;
                }
                dwd = new MysqlDatabaseHandler(conn.get());
                dwd.prepared();
                break;

            case POSTGRESQL:
                conn = loadPostgreSQLDriver();
                if (conn.isEmpty()) {
                    dwd = null;
                    break;
                }
                dwd = new PostgreSQLDatabaseHandler(conn.get());
                dwd.prepared();
                break;

            default:
                // Why this throw isn't force to be set at the creation of this function, and
                // can this stop my main function
                throw new UnsupportedOperationException("Unimplemented factory type");
        }
        return Optional.ofNullable(dwd);
    }

    public static DatabaseHandler createInmemoryDatastore() {
        return new InMemoryDatabaseHandler();
    }
}
