package asia.nghiango.dbhelper;

import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import asia.nghiango.entities.PageVisitRecordEntity;
import asia.nghiango.utilities.Log;

/**
 * Mysql Database Handler
 */
public class MysqlDatabaseHandler implements DatabaseHandler, SQLCommandInterface {
    private Connection conn;

    public MysqlDatabaseHandler(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void doBLANK(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doBLANK'");
    }

    @Override
    public Optional<Integer> doINSERT(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            int rs = stmt.executeUpdate(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "Can't insert into database, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLstatement: " + sqlStmt);
        }

        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Integer> doUPDATE(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            int rs = stmt.executeUpdate(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "Can't insert into database, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLstatement: " + sqlStmt);
        }

        return Optional.ofNullable(null);
    }

    @Override
    public void doDELETE(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doDELETE'");
    }

    @Override
    public void doMOVE(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doMOVE'");
    }

    @Override
    public void doWITH(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doWITH'");
    }

    @Override
    public void doCREATE(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doCREATE'");
    }

    @Override
    public void doALTER(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doALTER'");
    }

    public Optional<ResultSet> doSELECT(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            // handle any errors
            Log.printLog(Level.ERROR, "Fail to execute select statement, got SQLException error: " + ex.getMessage());
            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQL statement: " + sqlStmt);
        }

        return Optional.ofNullable(null);
    }

    @Override
    public Optional<ResultSet> getAll(String tableName, List<String> colNames) {
        String cols = "";
        for (String name : colNames) {
            if (cols.length() != 0) {
                cols = cols.concat(", ");
            }
            cols = cols.concat(name);
        }

        String sqlStmt = String.format("SELECT %s from %s", cols, tableName);

        Optional<ResultSet> rs = doSELECT(sqlStmt);
        return rs;
    }

    @Override
    public void createTable(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "Fail to create new table, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQL statement: " + sqlStmt);
        }
    }

    @Override
    public Optional<Integer> insert(String sqlStmt) {
        return doINSERT(sqlStmt);
    }
    @Override
    public Optional<Integer> update(String sqlStmt) {
        return doUPDATE(sqlStmt);
    }

    @Override
    public String constructInsertStatement(PageVisitRecordEntity entity) {
        Dictionary<String, String> de = entity.convertToDictionary();
        Enumeration<String> dkey = de.keys();
        String cols = "";
        String values = "";
        while (dkey.hasMoreElements()) {
            String k = dkey.nextElement();
            String v = de.get(k);

            if (cols.length() != 0) {
                cols = cols.concat(", ");
            }
            cols = cols.concat(k);

            if (values.length() != 0) {
                values = values.concat(", ");
            }
            values = values.concat(String.format("\"%s\"", v));
        }

        String stmtTemplate = """
                INSERT INTO record (%s)
                VALUES (%s);
                """;
        String sqlStmt = String.format(stmtTemplate, cols, values);

        return sqlStmt;
    }

    @Override
    public String constructUpdateStatement(PageVisitRecordEntity t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'constructUpdateStatement'");
    }
}
