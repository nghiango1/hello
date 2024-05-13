package asia.nghiango.dbhelper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 * DatabaseHandler
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

    public Optional<ResultSet> getAll(String tableName, List<DataField> colNames);

    public Optional<ResultSet> getByID(String tableName, List<DataField> colNames, DataField idFieldInfo, String idFieldValue);

    public String vendorTableNameHandler(String tableName);

    public String vendorFieldNameHandler(DataField dataField);

    public String vendorValueHandler(DataField dataField, String value);
}
