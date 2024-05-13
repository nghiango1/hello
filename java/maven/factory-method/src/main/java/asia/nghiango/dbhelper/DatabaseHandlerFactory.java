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
 * DatabaseHandlerFactory help create a {@link DatabaseHandler} interface
 * complican object, also handle Database connection part
 */
public class DatabaseHandlerFactory {

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
            Log.printLog(Level.ERROR, "SQLException: " + ex.getMessage());
            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
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
    public static Optional<Connection> loadPostgreSQLDriver(String connectionString) {
        try {
            Connection conn = DriverManager.getConnection(connectionString);
            return Optional.of(conn);
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, String.format(
                    "Fail to create new PostgreSQL connection, get SQLException error: %s\n", ex.getMessage()));

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
        }

        return Optional.ofNullable(null);
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
            case MYSQL:
                conn = loadMysqlDriver(Env.getEnvironmentValue("MYSQL_CONNECTION_STRING"));
                if (conn.isEmpty()) {
                    dwd = null;
                    break;
                }
                dwd = new MysqlDatabaseHandler(conn.get());
                break;

            case POSTGRESQL:
                conn = loadPostgreSQLDriver(Env.getEnvironmentValue("POSTGRESQL_CONNECTION_STRING"));
                if (conn.isEmpty()) {
                    dwd = null;
                    break;
                }
                dwd = new PostgreSQLDatabaseHandler(conn.get());
                break;

            default:
                // Why this throw isn't force to be set at the creation of this function, and
                // can this stop my main function
                throw new UnsupportedOperationException("Unimplemented factory type");
        }
        return Optional.ofNullable(dwd);
    }

    public static DatabaseHandler createInmemoryDatastore() {
        throw new UnsupportedOperationException("INMEN Datastore didn't implemented yet");
    }
}
