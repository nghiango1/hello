package asia.nghiango.dbhelper;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import asia.nghiango.utilities.Log;

/**
 * InsertSQLBuilder
 */
public class InsertSQLBuilder {

    private VendorSQLInterface vendor;

    private String tableName;

    private List<DataField> dataFields;

    private List<Dictionary<DataField, String>> values;

    public InsertSQLBuilder(VendorSQLInterface vendor) {
        this.vendor = vendor;
        this.dataFields = new ArrayList<DataField>();
        this.values = new ArrayList<Dictionary<DataField, String>>();
    }

    public InsertSQLBuilder addTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertSQLBuilder addField(DataField df) {
        this.dataFields.add(df);
        return this;
    }

    public InsertSQLBuilder addFields(List<DataField> dfs) {
        this.dataFields.addAll(dfs);
        return this;
    }

    public InsertSQLBuilder addValue(Dictionary<DataField, String> value) {
        this.values.add(value);
        return this;
    }

    public InsertSQLBuilder addValues(List<Dictionary<DataField, String>> values) {
        this.values.addAll(values);
        return this;
    }

    public String build() {
        String cols = "";
        for (DataField dataField : this.dataFields) {
            if (cols.length() != 0) {
                cols = cols.concat(", ");
            }
            cols = cols.concat(vendor.vendorHandler(dataField));
        }

        String values = "";
        for (Dictionary<DataField, String> value : this.values) {
            for (DataField df : this.dataFields) {
                if (values.length() != 0) {
                    values = values.concat(", ");
                }

                String fieldValue = value.get(df);
                if (fieldValue == null) {
                    Log.printLog(Level.WARNING, "A assigned inserted feild isn't have its value, skiping!");
                    Log.printLog(Level.DEBUG, String.format("Value = %s, Feild querry = %s", value, df.name));
                }
                values = values.concat(vendor.vendorValueHandler(df, fieldValue));
            }
        }

        String stmtTemplate = """
                INSERT INTO %s (%s)
                VALUES (%s);
                """;
        String sqlStmt = String.format(stmtTemplate, vendor.vendorTableNameHandler(this.tableName), cols, values);

        return sqlStmt;
    }
}
