package asia.nghiango.dbhelper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import asia.nghiango.entities.PageVisitRecordEntity;

/**
 * DataWriterDriver
 */
public interface DatabaseHandler {
    /**
     * Creatable is to vary between database so this help
     *
     * @param sqlStmt 
     */
    public void createTable(String sqlStmt);

    public Optional<Integer> insert(String sqlStmt);
    public Optional<Integer> update(String sqlStmt);

    public Optional<ResultSet> getAll(String tableName, List<String> colNames);

    public String constructInsertStatement(PageVisitRecordEntity entity);

    public String constructUpdateStatement(PageVisitRecordEntity t);
}
