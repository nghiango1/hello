package asia.nghiango.entities;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import asia.nghiango.dbhelper.DataField;
import asia.nghiango.model.Model;
import asia.nghiango.utilities.Util;

/**
 * Entities
 */
public abstract class Entity<T extends Model> {
    protected final Integer id;
    protected Boolean isDelete = false;
    protected final Timestamp dateUpdated;
    protected Timestamp dateCreated;

    protected static DataField[] baseDataFields = {
            new DataField("ID", JDBCType.INTEGER),
            new DataField("IS_DELETE", JDBCType.INTEGER),
            new DataField("DATE_UPDATED", JDBCType.INTEGER),
            new DataField("DATE_CREATED", JDBCType.INTEGER),
    };

    protected T data;

    public Entity(Integer id, Boolean isDelete, T t) {
        this.id = id;
        this.isDelete = isDelete;
        this.dateUpdated = Util.dateNow();
        this.dateCreated = Util.dateNow();
        this.data = t;
    }

    public Entity(Integer id, Boolean isDelete, T t, Timestamp dateCreated, Timestamp dateUpdated) {
        this.id = id;
        this.isDelete = isDelete;
        this.dateUpdated = dateUpdated;
        this.dateCreated = dateCreated;
        this.data = t;
    }

    public Entity(ResultSet rs) throws SQLException {
        this.id = Entity.readID(rs);
        this.isDelete = Entity.readIS_DELETE(rs);
        this.dateUpdated = Entity.readDATE_UPDATED(rs);
        this.dateCreated = Entity.readDATE_CREATED(rs);
        this.data = readData(rs);
    }

    public static int readID(ResultSet rs) throws SQLException {
        return rs.getInt("ID");
    }

    public static boolean readIS_DELETE(ResultSet rs) throws SQLException {
        return rs.getBoolean("IS_DELETE");
    }

    public static Timestamp readDATE_UPDATED(ResultSet rs) throws SQLException {
        return rs.getTimestamp("DATE_UPDATED");
    }

    public static Timestamp readDATE_CREATED(ResultSet rs) throws SQLException {
        return rs.getTimestamp("DATE_CREATED");
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
        return String.format("{id: %d, is_delete: %b, updated: %s, created: %s, data: %s}", this.id, this.isDelete,
                this.dateCreated.toString(), this.dateUpdated.toString(), this.data.toString());
    }

    public static String getTableName() {
        throw new UnsupportedOperationException("Base entity don't have table name");
    };

    public static List<DataField> getBaseDataFields() {
        return Arrays.asList(baseDataFields);
    };

    /**
     * Base entity does not have data information, this static method should be
     * overided by implemented concrete class/type
     *
     * @throws throw new UnsupportedOperationException(
     */
    public static List<DataField> getDataFields() {
        throw new UnsupportedOperationException(
                "Base entity don't have data, this should be replace in the concrete implement");
    };

    /**
     * Base entity does not have inner data information, this static method should
     * be overided by implemented concrete class/type
     * I want to share this code but it seem not posible, this may need some thing
     * like Zig compiler time template class instead (not possible in Java)
     *
     * @throws throw new UnsupportedOperationException(
     */
    public static List<DataField> getColumnNames() {
        List<DataField> colname = new ArrayList<DataField>();
        colname.addAll(getBaseDataFields());
        colname.addAll(getDataFields());

        return colname;
    }

    public abstract Dictionary<DataField, String> convertDataToDict();

    public Dictionary<DataField, String> convertToDictionary() {
        Dictionary<DataField, String> rs = convertDataToDict();
        rs.put(Entity.baseDataFields[0], this.id.toString());
        if (this.isDelete) {
            rs.put(Entity.baseDataFields[1], "1");
        } else {
            rs.put(Entity.baseDataFields[1], "0");
        }
        rs.put(Entity.baseDataFields[2], this.dateUpdated.toString());
        rs.put(Entity.baseDataFields[3], this.dateCreated.toString());

        return rs;
    }

}
