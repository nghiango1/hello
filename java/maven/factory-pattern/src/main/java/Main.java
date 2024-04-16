import java.sql.Timestamp;
import java.util.Optional;

import asia.nghiango.dao.*;
import asia.nghiango.dbhelper.DWDFactory;
import asia.nghiango.dbhelper.DataWriterDriver;
import asia.nghiango.dbhelper.MysqlDWD;
import asia.nghiango.dbhelper.DWDFactory.DWDType;
import asia.nghiango.entities.Entity;
import asia.nghiango.model.WebAnalyticStat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    /**
     * This load mysql driver then create a new connection to mysql database
     *
     * @return null if there is any error
     * @return Connection to the databse if connect is success full
     */
    @SuppressWarnings("deprecation")
    public static Optional<Connection> loadDriver() {
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

    public static void main(String[] args) {
        DWDFactory DWDFactoryInstance = new DWDFactory();
        DataWriterDriver dwd = DWDFactoryInstance.createDWD(DWDType.IN_MEM);
        WebAnalyticStatDAO wasDao = new WebAnalyticStatDAO(dwd);

        Timestamp requestTime = new Timestamp(11000);
        Timestamp serveTime = new Timestamp(14000);
        Timestamp leaveTime = new Timestamp(18000);
        WebAnalyticStat stat = new WebAnalyticStat(
                "https://nghiango.asia",
                "/",
                requestTime,
                serveTime,
                leaveTime,
                "https://google.com",
                "chrome",
                "pc",
                "linux");

        Optional<Connection> conn = loadDriver();
        dwd = new MysqlDWD(conn.get());
        wasDao = new WebAnalyticStatDAO(dwd);

        // id: 0
        wasDao.save(stat);
        // id: 1
        Entity element2 = wasDao.save(stat);
        // id: 2
        wasDao.save(stat);
        for (Entity wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        }

        wasDao.delete(element2);
        for (Entity wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        }

        Optional<Entity> getElmt2 = wasDao.get(1); // 0-index
        if (getElmt2.isEmpty()) {
            System.out.println("Element 2 not found!");
        }
    }
}
