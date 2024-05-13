package asia.nghiango.dbhelper;

import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

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
        throw new UnsupportedOperationException("Unimplemented method 'doBLANK'");
    }

    @Override
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
        throw new UnsupportedOperationException("Unimplemented method 'doDELETE'");
    }

    @Override
    public void doMOVE(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'doMOVE'");
    }

    @Override
    public void doWITH(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'doWITH'");
    }

    @Override
    public void doCREATE(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'doCREATE'");
    }

    @Override
    public void doALTER(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'doALTER'");
    }

    @Override
    public Optional<ResultSet> getAll(String tableName, List<DataField> colNames) {
        SelectSQLBuilder sqlBuilder = new SelectSQLBuilder(this);
        String sqlStmt = sqlBuilder.setTablename(tableName).addSelectedFeilds(colNames).build();

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
    public String vendorTableNameHandler(String tableName) {
        return String.format("%s", tableName);
    }

    @Override
    public String vendorFieldNameHandler(DataField dataField) {
        switch (dataField.type) {
            default:
                return String.format("%s", dataField.name);
        }
    }

    @Override
    public String vendorValueHandler(DataField dataField, String value) {
        // MySQL insert value look like this "any"
        switch (dataField.type) {
            default:
                return String.format("\"%s\"", value);
        }
    }
}
