package asia.nghiango.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import asia.nghiango.model.Model;

/**
 * Entities
 */
public abstract class Entity<T extends Model> {
    protected final Integer id;
    protected Boolean isDelete = false;

    protected static String[] baseColumnNames = {
            "ID",
            "IS_DELETE"
    };

    protected T data;

    public Entity(Integer id, Boolean isDelete, T t) {
        this.id = id;
        this.isDelete = isDelete;
        this.data = t;
    }

    public Entity(ResultSet rs) throws SQLException {
        this.id = Entity.readID(rs);
        this.isDelete = Entity.readIS_DELETE(rs);
        this.data = readData(rs);
    }

    public static int readID(ResultSet rs) throws SQLException {
        return rs.getInt("ID");
    }

    public static boolean readIS_DELETE(ResultSet rs) throws SQLException {
        return rs.getInt("IS_DELETE") == 1;
    }

    public abstract T readData(ResultSet rs) throws SQLException;

    public void setData(T data) {
        this.data = data;
    };

    public Integer getId() {
        return this.id;
    }

    public T getData() {
        return this.data;
    }

    public Boolean isDelete() {
        return this.isDelete;
    }

    public void remove() {
        this.isDelete = true;
    }

    public String toString() {
        return String.format("{id: %d, is_delete: %b, data: %s}", this.id, this.isDelete, this.data.toString());
    }

    public static String getTableName() {
        throw new UnsupportedOperationException("Base entity don't have table name");
    };

    public static List<String> getBaseColumnNames() {
        return Arrays.asList(baseColumnNames);
    };

    /**
     * Base entity does not have data information, this static method should be overided by implemented concrete class/type
     *
     * @throws throw new UnsupportedOperationException( 
     */
    public static List<String> getDataColumnNames() {
        throw new UnsupportedOperationException(
                "Base entity don't have data, this should be replace in the concrete implement");
    };

    /**
     * Base entity does not have inner data information, this static method should be overided by implemented concrete class/type
     * I want to share this code but it seem not posible, this may need some thing like Zig compiler time template class instead (not possible in Java)
     *
     * @throws throw new UnsupportedOperationException( 
     */
    public static List<String> getColumnNames() {
        List<String> colname = new ArrayList<String>();
        colname.addAll(getBaseColumnNames());
        colname.addAll(getDataColumnNames());

        return colname;
    }

    public abstract Dictionary<String, String> convertDataToDict();

    public Dictionary<String, String> convertToDictionary() {
        Dictionary<String, String> rs = convertDataToDict();
        rs.put(Entity.baseColumnNames[0], this.id.toString());
        if (this.isDelete) {
            rs.put(Entity.baseColumnNames[1], "1");
        } else {
            rs.put(Entity.baseColumnNames[1], "0");
        }

        return rs;
    }

}
