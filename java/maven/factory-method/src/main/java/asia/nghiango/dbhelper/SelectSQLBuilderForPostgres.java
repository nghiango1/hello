package asia.nghiango.dbhelper;

/**
 * SelectSQLBuilderForPostgres
 */
public class SelectSQLBuilderForPostgres extends SelectSQLBuilder {

    @Override
    public SelectSQLBuilder addWhereCommpairExpression(String feildName, String Operation, String expessionValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addWhereCommpairExpression'");
    }

    @Override
    public String build() {
        String cols = "";
        for (DataField name : this.selectedField) {
            if (cols.length() != 0) {
                cols = cols.concat(", ");
            }
            cols = cols.concat(String.format("\"%s\"", name.name));
        }

        return String.format("SELECT %s from \"%s\"", cols, this.tableName);
    }

}
