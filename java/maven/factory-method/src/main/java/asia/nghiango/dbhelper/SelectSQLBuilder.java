package asia.nghiango.dbhelper;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 */
public abstract class SelectSQLBuilder {

    protected String tableName;

    protected List<DataField> selectedField;

    protected String whereExpression;

    public SelectSQLBuilder() {
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

    public abstract SelectSQLBuilder addWhereCommpairExpression(String feildName, String Operation,
            String expessionValue);

    public abstract String build();

}
