package asia.nghiango.dbhelper;

import java.sql.ResultSet;
import java.util.Optional;

import org.postgresql.core.SqlCommandType;

/**
 * SQLCommandInterface share commond sql statment
 */
public interface SQLCommandInterface {

    public void BLANK(String sqlStmt);

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    public Optional<Integer> INSERT(String sqlStmt);

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    public Optional<Integer> UPDATE(String sqlStmt);

    public void DELETE(String sqlStmt);

    public void MOVE(String sqlStmt);

    public Optional<ResultSet> SELECT(String sqlStmt);

    public void WITH(String sqlStmt);

    public void CREATE(String sqlStmt);

    public void ALTER(String sqlStmt);
}
