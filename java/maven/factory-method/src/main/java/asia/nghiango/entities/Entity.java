package asia.nghiango.entities;

import java.lang.System.Logger.Level;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Optional;

import asia.nghiango.model.Model;
import asia.nghiango.utilities.Log;

/**
 * Entities
 */
public abstract class Entity {
    private final Integer id;
    private Boolean isDeleted = false;

    private static String[] columnNames = {
            "ID",
            "IS_DELETE"
    };

    private Model data;

    public Entity(Integer id, Model t) {
        this.data = t;
        this.id = id;
    }

    /**
     * An SQL result row can be convert to their coresponse entity object instance
     *
     * @param rs    The {@link ResultSet} that return when executing a SQL statement
     * @param model The {@link Model} that we want this SQL output to convert into
     *              their coresponse Entity
     * @return Entity that match with wanted model
     * @return Null if there is any error
     */
    public static Optional<Entity> convertRowToEntity(ResultSet rs, Model model) {
        Boolean isDeleted = false;
        Integer entityId = 0;

        try {
            isDeleted = rs.getInt("IS_DELETE") == 1;
            entityId = rs.getInt("ID");
        } catch (SQLException e) {
            Log.printLog(Level.ERROR, "Can't get must have infomation of Entity, got SQLException error: " + e.toString());
            return Optional.ofNullable(null);
        }

        model.setDataFromRow(rs);
        Optional<Entity> ret = EntityFactory.create(entityId, model.getModelType(), model);
        if (!ret.isEmpty()) {
            ret.get().isDeleted = isDeleted;
        }
        return ret;
    }

    public Integer getId() {
        return this.id;
    }

    public Model getData() {
        return this.data;
    }

    public Boolean isDelete() {
        return this.isDeleted;
    }

    public void remove() {
        this.isDeleted = true;
    }

    public String toString() {
        return String.format("{id: %d, is_delete: %b, data: %s}", this.id, this.isDeleted, this.data.toString());
    }

    public Dictionary<String, String> convertToDictionary() {
        Dictionary<String, String> rs = this.data.convertDict();
        rs.put(Entity.columnNames[0], this.id.toString());
        if (this.isDeleted) {
            rs.put(Entity.columnNames[1], "1");
        } else {
            rs.put(Entity.columnNames[1], "0");
        }

        return rs;
    }

    public String getTableName() {
        return data.getName();
    }

    public static List<String> getColumnNames() {
        return Arrays.asList(columnNames);
    }
}
