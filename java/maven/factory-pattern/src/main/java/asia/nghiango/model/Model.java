package asia.nghiango.model;

import java.sql.ResultSet;
import java.util.Dictionary;

/**
 * Model
 */
public interface Model {

    public Model convertRowToModel(ResultSet rs);
    public void setDataFromRow(ResultSet rs);
    public String getName();
    public Dictionary<String, String> convertDict();
}
