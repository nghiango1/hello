package asia.nghiango.dbhelper;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 */
public abstract class SelectSQLBuilder {

    protected String tableName;

    protected List<String> selectedField;

    protected String whereExpression;

    public SelectSQLBuilder() {
        this.selectedField = new ArrayList<String>();
    }

    public SelectSQLBuilder setTablename(String tableName) {
        this.tableName = tableName;
        return this;
    };

    public SelectSQLBuilder addSelectedFeild(String feildName) {
        this.selectedField.add(feildName);
        return this;
    };

    public SelectSQLBuilder addSelectedFeilds(List<String> feildList) {
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
