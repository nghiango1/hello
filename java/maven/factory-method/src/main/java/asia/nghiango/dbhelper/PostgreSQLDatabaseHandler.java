package asia.nghiango.dbhelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import asia.nghiango.entities.Entity;
import asia.nghiango.entities.EntityFactory;
import asia.nghiango.model.Model;
import asia.nghiango.model.PageVisitRecord;
import asia.nghiango.utilities.Log;

/**
 * PostgreSQLDatabaseHandler
 *
 */
public class PostgreSQLDatabaseHandler implements DatabaseHandler {
    private Connection conn;
    private Integer currentId = 1;

    public PostgreSQLDatabaseHandler(Connection conn) {
        this.conn = conn;
    }

    public void prepared() {
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(PageVisitRecord.createTablePostgreSQLCommand());
        } catch (SQLException ex) {
            // handle any errors
            Log.printLog(Level.ERROR, "Fail to create new table, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLStatment: " + PageVisitRecord.createTablePostgreSQLCommand());
        }
    }

    private Optional<ResultSet> execSelectSql(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "Fail to execute select statement, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLStatment: " + PageVisitRecord.createTablePostgreSQLCommand());
        }

        return Optional.ofNullable(null);
    }

    @Override
    public List<Entity> getAll(String tableName, List<String> colNames) {
        List<Entity> arrLst = new ArrayList<Entity>();

        SelectSQLBuilder sqlBuilder = new SelectSQLBuilderForPostgreSLQ();
        String sqlStmt = sqlBuilder.setTablename(tableName).addSelectedFeilds(colNames).build();

        Optional<ResultSet> rs = execSelectSql(sqlStmt);
        if (rs.isEmpty()) {
            return arrLst;
        }

        ResultSet rSet = rs.get();

        try {
            // if (!rSet.first())
            // return arrLst;

            while (rSet.next()) {
                Optional<Entity> entity = Entity.convertRowToEntity(rSet, new PageVisitRecord());
                if (!entity.isEmpty()) {
                    if (entity.get().isDelete()) {
                        // skip if the entity is marks as removed
                        continue;
                    }
                    arrLst.add(entity.get());
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            Log.printLog(Level.ERROR, "False to read result set, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "\tSQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "\tVendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return arrLst;
    }

    @Override
    public Optional<Entity> get(String tableName, List<String> colNames, int id) {
        return Optional.ofNullable(null);
    }

    private String constructInsertStatement(Entity entity) {
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
            cols = cols.concat(String.format("\"%s\"", k));

            if (values.length() != 0) {
                values = values.concat(", ");
            }

            // PosgresSQL insert look like this '"string"'
            // The boolean recevied interger 1/0 so we use '1'/'0' for ID_DELETE instead
            if (k == "IS_DELETE" || k == "ID") {
                values = values.concat(String.format("'%s'", v));
            } else {
                values = values.concat(String.format("'\"%s\"'", v));
            }
        }

        String stmtTemplate = """
                INSERT INTO record (%s)
                VALUES (%s);
                """;
        String sqlStmt = String.format(stmtTemplate, cols, values);

        return sqlStmt;
    }

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    private int execInsertStatement(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            int rs = stmt.executeUpdate(sqlStmt);
            return rs;
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "False to insert into database, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "\tSQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "\tVendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "\tSQLStatement: " + sqlStmt);
        }

        return 0;
    }

    @Override
    public Entity save(Model t) {
        Entity entity = EntityFactory.create(this.currentId, t.getModelType(), t).get();
        this.currentId += 1;
        String sqlStmt = constructInsertStatement(entity);
        execInsertStatement(sqlStmt);
        return entity;
    }

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    private int execUpdateStatement(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            int rs = stmt.executeUpdate(sqlStmt);
            return rs;
        } catch (SQLException ex) {
            Log.printLog(Level.ERROR, "False to update entity, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
            Log.printLog(Level.DEBUG, "SQLStatement: " + sqlStmt);
        }

        return 0;
    }

    private String constructUpdateStatement(Entity entity) {
        String id = entity.getId().toString();
        String isDelete = "0";
        if (entity.isDelete())
            isDelete = "1";
            
        Dictionary<String, String> de = entity.convertToDictionary();
        Enumeration<String> dkey = de.keys();
        String values = "";
        while (dkey.hasMoreElements()) {
            String k = dkey.nextElement();
            String v = de.get(k);

            // Skip ID field as it already been handle
            if (k == "ID" || k == "IS_DELETE") {
                continue;
            }

            values = values.concat(",\n");
            values = values.concat(String.format("\"%s\" = '\"%s\"'", k, v));
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
    public void update(Entity t) {
        String sqlStmt = constructUpdateStatement(t);
        execUpdateStatement(sqlStmt);
    }

    @Override
    public void delete(Entity t) {
        t.remove();
        update(t);
    }

}
