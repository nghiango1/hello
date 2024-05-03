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
import asia.nghiango.utilities.Env;
import asia.nghiango.utilities.Log;

/**
 * PlaintextDWD
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

            if (Env.isVerbose()) {
                System.out.println("\tSQLState: " + ex.getSQLState());
                System.out.println("\tVendorError: " + ex.getErrorCode());
                System.out.println("\tSQLStatment: " + PageVisitRecord.createTablePostgreSQLCommand());
            }
        }

        return Optional.ofNullable(null);
    }

    @Override
    public List<Entity> getAll(String tableName, List<String> colNames) {
        List<Entity> arrLst = new ArrayList<Entity>();
        String cols = "";
        for (String name : colNames) {
            if (cols.length() != 0) {
                cols = cols.concat(", ");
            }
            cols = cols.concat(String.format("\"%s\"", name));
        }

        String sqlStmt = String.format("SELECT %s from \"%s\"", cols, tableName);

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

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    private int execInsertStatement(Entity entity) {
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

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    private int execUpdateStatement(Entity entity) {
        Dictionary<String, String> de = entity.convertToDictionary();
        Enumeration<String> dkey = de.keys();
        String id = String.format("'%s'", de.get("ID"));
        String values = "";
        while (dkey.hasMoreElements()) {
            String k = dkey.nextElement();
            String v = de.get(k);

            if (k == "ID") {
                continue;
            }

            if (values.length() != 0) {
                values = values.concat(",\n");
            }

            // PosgresSQL insert,update value look like this '"string"' / 'number'
            if (k == "IS_DELETE") {
                values = values.concat(String.format("\"%s\" = '%s'", k, v));
            } else {
                values = values.concat(String.format("\"%s\" = '\"%s\"'", k, v));
            }
        }

        if (id == null) {
            Log.printLog(Level.ERROR, "Entity doesn't have id field, abort!");
            return 0;
        }

        String stmtTemplate = """
                UPDATE record SET
                %s
                WHERE \"ID\" = %s;
                """;
        String sqlStmt = String.format(stmtTemplate, values, id);

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

    @Override
    public Entity save(Model t) {
        Entity result = EntityFactory.create(this.currentId, t.getModelType(), t).get();
        this.currentId += 1;
        execInsertStatement(result);
        return result;
    }

    @Override
    public void update(Entity t) {
        execUpdateStatement(t);
    }

    @Override
    public void delete(Entity t) {
        t.remove();
        execUpdateStatement(t);
    }

}
