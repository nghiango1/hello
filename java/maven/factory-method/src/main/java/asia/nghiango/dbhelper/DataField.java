package asia.nghiango.dbhelper;

import java.sql.JDBCType;

public class DataField {
    public final String name;
    public final JDBCType type;

    public DataField(String name, JDBCType type){
        this.name = name;
        this.type = type;
    }
}

