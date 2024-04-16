
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
import asia.nghiango.model.Model;
import asia.nghiango.model.WebAnalyticStat;

/**
 * PlaintextDWD
 */
public class MysqlDWD implements DataWriterDriver {
    private Connection conn;
    private Integer currentId = 0;

    public MysqlDWD(Connection conn) {
        this.conn = conn;
    }

    private Optional<ResultSet> execSelectSql(String sqlStmt) {
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStmt);
            return Optional.of(rs);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return Optional.ofNullable(null);
    }

    @Override
    public List<Entity> getAll() {
        List<Entity> arrLst = new ArrayList<Entity>();
        Optional<ResultSet> rs = execSelectSql("SELECT * from record");
        if (rs.isEmpty()) {
            System.out.println("Error exec SQL select command");
            return arrLst;
        }

        ResultSet rSet = rs.get();

        try {
            // if (!rSet.first())
            //     return arrLst;

            while (rSet.next()) {
                Optional<Entity> entity = Entity.convertRowToEntity(rSet, new WebAnalyticStat());
                if (entity.isEmpty()) {
                    System.out.printf("Error readding at row %s\n", rSet.getCursorName());
                } else {
                    arrLst.add(entity.get());
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return arrLst;
    }

    @Override
    public Optional<Entity> get(int id) {
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
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return 0;
    }

    @Override
    public Entity save(Model t) {
        Entity result = new Entity(this.currentId, t);
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

}
