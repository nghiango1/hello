package asia.nghiango.entities;

import java.sql.ResultSet;
import java.util.Dictionary;
import java.util.Optional;

import asia.nghiango.model.Model;

/**
 * Entities
 */
public class Entity {
    private final Integer id;
    private Boolean isDeleted = false;

    private static String[] colName = {
            "ID",
            "IS_DELETE"
    };

    private Model data;

    public Entity(Integer id, Model t) {
        this.data = t;
        this.id = id;
    }

    public static Optional<Entity> convertRowToEntity(ResultSet rs, Model model) {
        try {
            model.setDataFromRow(rs);
            Entity ret = new Entity(rs.getInt("ID"), model);
            ret.isDeleted = rs.getInt("IS_DELETE") == 1;
            return Optional.of(ret);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return Optional.ofNullable(null);
    }

    public Integer getId() {
        return this.id;
    }

    public Model getData() {
        return this.data;
    }

    public Boolean isRemoved() {
        return this.isDeleted;
    }

    public void remove() {
        this.isDeleted = true;
    }

    public String toString() {
        return String.format("{id: %d, data: %s}", this.id, this.data.toString());
    }

    public Dictionary<String, String> convertToDictionary() {
        Dictionary<String, String> rs = this.data.convertDict();
        rs.put(Entity.colName[0], this.id.toString());
        if (this.isDeleted) {
            rs.put(Entity.colName[1], "1");
        } else {
            rs.put(Entity.colName[1], "0");
        }

        return rs;
    }
}
