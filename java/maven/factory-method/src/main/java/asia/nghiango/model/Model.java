package asia.nghiango.model;

import java.sql.ResultSet;
import java.util.Dictionary;

import asia.nghiango.entities.EntityFactory.SupportedType;

/**
 * Model
 */
public interface Model {

    public Model convertRowToModel(ResultSet rs);
    public void setDataFromRow(ResultSet rs);
    public SupportedType getModelType();
    public String getName();
    public Dictionary<String, String> convertDict();
}
