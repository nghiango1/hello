package asia.nghiango.dbhelper;

import java.util.Optional;

import asia.nghiango.dbhelper.DWDFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DWDFactory, now i actually hate this pattern the moment I have to implement
 * it
 */
public class DWDFactory {

    public static enum DWDType {
        IN_MEM,
        FILE,
        MYSQL,
        ORACLE,
        PSQL
    }

    /**
     * This load mysql driver then create a new connection to mysql database
     *
     * @return null if there is any error
     * @return Connection to the databse if connect is success full
     */
    @SuppressWarnings("deprecation")
    public static Optional<Connection> loadMysqlDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return Optional.ofNullable(null);
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webstat?" +
                    "user=root&password=example");
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
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
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
    public Optional<DataWriterDriver> createDWD(DWDType type) {
        DataWriterDriver dwd;
        Optional<Connection> conn;
        switch (type) {
            case IN_MEM:
                dwd = new InMemoryDWD();
                break;

            case MYSQL:
                conn = loadMysqlDriver();
                if (conn.isEmpty()) {
                    dwd = null;
                    break;
                }
                dwd = new MysqlDWD(conn.get());
                dwd.prepared();
                break;

            case PSQL:
                conn = loadPostgreSQLDriver();
                if (conn.isEmpty()) {
                    dwd = null;
                    break;
                }
                dwd = new PostgreSQLDWD(conn.get());
                dwd.prepared();
                break;

            default:
                throw new UnsupportedOperationException("Unimplemented factory type");
        }
        return Optional.ofNullable(dwd);
    }

    public DataWriterDriver createInMemDWD() {
        return new InMemoryDWD();
    }
}
