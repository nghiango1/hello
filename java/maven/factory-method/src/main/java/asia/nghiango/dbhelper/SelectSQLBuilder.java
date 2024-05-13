package asia.nghiango.dbhelper;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 * 
 */
public class SelectSQLBuilder {

    private DatabaseHandler vendor;

    private String tableName;

    private List<DataField> selectedField;

    private Optional<String> whereExpression;

    public SelectSQLBuilder(DatabaseHandler vendor) {
        this.vendor = vendor;
        this.whereExpression = Optional.ofNullable(null);
        this.selectedField = new ArrayList<DataField>();
    }

    public SelectSQLBuilder setTablename(String tableName) {
        this.tableName = tableName;
        return this;
    };

    public SelectSQLBuilder addSelectedFeild(DataField feildName) {
        this.selectedField.add(feildName);
        return this;
    };

    public SelectSQLBuilder addSelectedFeilds(List<DataField> feildList) {
        this.selectedField.addAll(feildList);
        return this;
    };

    public SelectSQLBuilder setFindById(DataField df, String value) {
        String exps = String.format("%s = %s", vendor.vendorFieldNameHandler(df), vendor.vendorValueHandler(df, value));
        this.whereExpression = Optional.of(exps);
        return this;
    };

    public String build() {
        String cols = "";
        for (DataField df : this.selectedField) {
            if (cols.length() != 0) {
                cols = cols.concat(", ");
            }
            cols = cols.concat(vendor.vendorFieldNameHandler(df));
        }

        String sqlStmt = String.format("SELECT %s FROM %s", cols, vendor.vendorTableNameHandler(this.tableName));
        if (!this.whereExpression.isEmpty()) {
            String where = String.format(" WHERE %s", this.whereExpression.get());
            sqlStmt = sqlStmt.concat(where);
        }

        return sqlStmt;
    }
}
