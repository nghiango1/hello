package asia.nghiango.dbhelper;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 */
public class SelectSQLBuilder {

    protected VendorSQLInterface vendorSQLHandler;

    protected String tableName;

    protected List<DataField> selectedField;

    protected String whereExpression;

    public SelectSQLBuilder(VendorSQLInterface vendor) {
        this.vendorSQLHandler = vendor;
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

    public SelectSQLBuilder addWhereExpression(String whereExpression) {
        this.whereExpression = whereExpression;
        return this;
    };

    public String build() {
        String cols = "";
        for (DataField df : this.selectedField) {
            if (cols.length() != 0) {
                cols = cols.concat(", ");
            }
            cols = cols.concat(vendorSQLHandler.vendorHandler(df));
        }

        return String.format("SELECT %s from \"%s\"", cols, this.tableName);
    }

}
