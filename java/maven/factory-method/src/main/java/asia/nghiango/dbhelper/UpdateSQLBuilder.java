package asia.nghiango.dbhelper;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import asia.nghiango.utilities.Log;

/**
 * UpdateSQLBuilder
 */
public class UpdateSQLBuilder {

    private VendorSQLInterface vendor;

    private String tableName;

    private List<DataField> dataFields;

    private Dictionary<DataField, String> value;

    private String whereExpresion;

    public UpdateSQLBuilder(VendorSQLInterface vendor) {
        this.vendor = vendor;
        this.dataFields = new ArrayList<DataField>();
    }

    public UpdateSQLBuilder addTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UpdateSQLBuilder addField(DataField df) {
        this.dataFields.add(df);
        return this;
    }

    public UpdateSQLBuilder addFields(List<DataField> dfs) {
        this.dataFields.addAll(dfs);
        return this;
    }

    public UpdateSQLBuilder setValue(Dictionary<DataField, String> value) {
        this.value = value;
        return this;
    }

    public UpdateSQLBuilder setUpdateByID(DataField df, String value) {
        String idFieldName = vendor.vendorFieldNameHandler(df);
        String idFieldValue = vendor.vendorValueHandler(df, value);
        this.whereExpresion = String.format("%s = %s", idFieldName, idFieldValue);
        return this;
    }

    public String build() {
        String values = "";
        for (DataField df : this.dataFields) {
            if (values.length() != 0) {
                values = values.concat(",\n");
            }

            String fieldValue = this.value.get(df);
            if (fieldValue == null) {
                Log.printLog(Level.WARNING, "A assigned inserted feild isn't have its value, skiping!");
                Log.printLog(Level.DEBUG, String.format("Value = %s, Feild querry = %s", this.value, df.name));
            }
            String formatName = vendor.vendorFieldNameHandler(df);
            String formatValue = vendor.vendorValueHandler(df, fieldValue);
            values = values.concat(String.format("%s = %s", formatName, formatValue));
        }

        String stmtTemplate = """
                UPDATE %s SET
                %s
                WHERE %s;
                """;
        String sqlStmt = String.format(stmtTemplate, vendor.vendorTableNameHandler(this.tableName), values, this.whereExpresion);
        return sqlStmt;
    }
}
