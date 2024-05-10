package asia.nghiango.dbhelper;

import java.sql.ResultSet;
import java.util.Optional;

import org.postgresql.core.SqlCommandType;

/**
 * SQLCommandInterface share commond sql statment
 */
public interface SQLCommandInterface {

    public void doBLANK(String sqlStmt);

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    public Optional<Integer> doINSERT(String sqlStmt);

    // either (1) the row count for SQL Data Manipulation Language (DML) statements
    // or (2) 0 for SQL statements that return nothing
    public Optional<Integer> doUPDATE(String sqlStmt);

    public void doDELETE(String sqlStmt);

    public void doMOVE(String sqlStmt);

    public Optional<ResultSet> doSELECT(String sqlStmt);

    public void doWITH(String sqlStmt);

    public void doCREATE(String sqlStmt);

    public void doALTER(String sqlStmt);
}
