package asia.nghiango.dbhelper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import asia.nghiango.entities.PageVisitRecordEntity;

/**
 * PlaintextDWD
 */
public class InMemoryDatabaseHandler implements DatabaseHandler {

    @Override
    public void createTable(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTable'");
    }

    @Override
    public Optional<Integer> insert(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public Optional<Integer> update(String sqlStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<ResultSet> getAll(String tableName, List<DataField> colNames) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public String constructInsertStatement(PageVisitRecordEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'constructInsertStatement'");
    }

    @Override
    public String constructUpdateStatement(PageVisitRecordEntity t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'constructUpdateStatement'");
    }
}
