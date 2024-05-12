package asia.nghiango.dbhelper;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import asia.nghiango.entities.PageVisitRecordEntity;
import asia.nghiango.utilities.Log;

/**
 * PostgreSQLDatabaseHandler
 *
 */
public class PostgreSQLDatabaseHandler implements DatabaseHandler, SQLCommandInterface, VendorSQLInterface {
    private Connection conn;

    public PostgreSQLDatabaseHandler(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void doBLANK(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'BLANK'");
    }

    @Override
    public Optional<Integer> doINSERT(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            int rs = stmt.executeUpdate(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "False to insert into database, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLStatement: " + sqlStmt);
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
            Log.printLog(Level.ERROR, "False to update entity, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLStatement: " + sqlStmt);
        }

        return Optional.ofNullable(null);
    }

    @Override
    public void doDELETE(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'DELETE'");
    }

    @Override
    public void doMOVE(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'MOVE'");
    }

    @Override
    public Optional<ResultSet> doSELECT(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "Fail to execute select statement, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLStatment: " + sqlStmt);
        }

        return Optional.ofNullable(null);
    }

    @Override
    public void doWITH(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'WITH'");
    }

    @Override
    public void doCREATE(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException ex) {
            // handle any errors
            Log.printLog(Level.ERROR, "Fail to create new table, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLStatment: " + sqlStmt);
        }
    }

    @Override
    public void doALTER(String sqlStmt) {
        throw new UnsupportedOperationException("Unimplemented method 'ALTER'");
    }

    @Override
    public void createTable(String sqlStmt) {
        doCREATE(sqlStmt);
    }

    @Override
    public Optional<ResultSet> getAll(String tableName, List<DataField> colNames) {
        SelectSQLBuilder sqlBuilder = new SelectSQLBuilder(this);
        String sqlStmt = sqlBuilder.setTablename(tableName).addSelectedFeilds(colNames).build();

        Optional<ResultSet> rs = doSELECT(sqlStmt);
        return rs;
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
        InsertSQLBuilder builder = new InsertSQLBuilder(this);
        String sqlStmt = builder.addTableName(PageVisitRecordEntity.getTableName())
                .addFields(PageVisitRecordEntity.getColumnNames())
                .addValue(entity.convertToDictionary())
                .build();
        return sqlStmt;
    }

    @Override
    public String constructUpdateStatement(PageVisitRecordEntity entity) {
        String id = entity.getId().toString();
        String isDelete = "0";
        if (entity.isDelete())
            isDelete = "1";

        Dictionary<DataField, String> de = entity.convertToDictionary();
        Enumeration<DataField> dkey = de.keys();
        String values = "";
        while (dkey.hasMoreElements()) {
            DataField k = dkey.nextElement();
            String v = de.get(k);

            // Skip ID field as it already been handle
            if (k.name == "ID" || k.name == "IS_DELETE") {
                continue;
            }

            values = values.concat(",\n");
            values = values.concat(String.format("\"%s\" = '\"%s\"'", k.name, v));
        }

        // PosgresSQL insert,update value look like this '"string"' / 'number'
        String stmtTemplate = """
                UPDATE record SET
                \"IS_DELETE\"='%s'%s
                WHERE \"ID\" = %s;
                """;
        String sqlStmt = String.format(stmtTemplate, isDelete, values, id);
        return sqlStmt;
    }

    @Override
    public String vendorTableNameHandler(String tableName) {
        return String.format("\"%s\"", tableName);
    }

    @Override
    public String vendorHandler(DataField dataField) {
        switch (dataField.type) {
            default:
                return String.format("\"%s\"", dataField.name);
        }
    }

    @Override
    public String vendorValueHandler(DataField dataField, String value) {
        // PosgresSQL insert string value look like this '"string"'
        // The integer, boolean (recevied interger 1/0) look like this 'number'
        switch (dataField.type) {
            case INTEGER:
            case BOOLEAN:
                return String.format("'%s'", value);
            default:
                return String.format("'\"%s\"'", value);
        }
    }
}
