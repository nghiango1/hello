package asia.nghiango.dbhelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import asia.nghiango.entities.Entity;
import asia.nghiango.entities.EntityFactory;
import asia.nghiango.model.Model;
import asia.nghiango.model.PageVisitRecord;
import asia.nghiango.utilities.Env;

/**
 * MysqlDWD
 */
public class MysqlDatabaseHandler implements DatabaseHandler {
    private Connection conn;
    private Integer currentId = 1;

    public MysqlDatabaseHandler(Connection conn) {
        this.conn = conn;
    }

    private Optional<ResultSet> execSelectSql(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("Fail to execute select statement, got SQLException error: " + ex.getMessage());
            if (Env.isVerbose()) {
                System.out.println("\tSQLState: " + ex.getSQLState());
                System.out.println("\tVendorError: " + ex.getErrorCode());
                System.out.println("\tSQL statement: " + sqlStmt);
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
            cols = cols.concat(name);
        }

        String sqlStmt = String.format("SELECT %s from %s", cols, tableName);

        Optional<ResultSet> rs = execSelectSql(sqlStmt);
        if (rs.isEmpty()) {
            return arrLst;
        }

        ResultSet rSet = rs.get();

        try {
            while (rSet.next()) {
                Optional<Entity> entity = Entity.convertRowToEntity(rSet, new PageVisitRecord());
                if (entity.isEmpty()) {
                    System.out.printf("Error readding at row %s\n", rSet.getCursorName());
                } else {
                    arrLst.add(entity.get());
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("Fail to SQLException: " + ex.getMessage());
            if (Env.isVerbose()) {
                System.out.println("\tSQLException: " + ex.getMessage());
                System.out.println("\tSQLState: " + ex.getSQLState());
                System.out.println("\tVendorError: " + ex.getErrorCode());
            }
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

        try {
            Statement stmt = this.conn.createStatement();
            int rs = stmt.executeUpdate(sqlStmt);
            return rs;
        } catch (SQLException ex) {
            System.out.println("Can't insert into database, got SQLException error: " + ex.getMessage());
            if (Env.isVerbose()) {
                System.out.println("\tSQLState: " + ex.getSQLState());
                System.out.println("\tVendorError: " + ex.getErrorCode());
                System.out.println("\tSQLstatement: " + sqlStmt);
            }
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
    }

    @Override
    public void delete(Entity t) {
        t.remove();
    }

    @Override
    public void prepared() {
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(PageVisitRecord.createTableMySQLCommand());
        } catch (SQLException ex) {
            System.out.println("Fail to create new table, got SQLException error: " + ex.getMessage());
            if (Env.isVerbose()) {
                System.out.println("\tSQLState: " + ex.getSQLState());
                System.out.println("\tVendorError: " + ex.getErrorCode());
                System.out.println("\tSQL statement: " + PageVisitRecord.createTableMySQLCommand());
            }
        }
    }

}