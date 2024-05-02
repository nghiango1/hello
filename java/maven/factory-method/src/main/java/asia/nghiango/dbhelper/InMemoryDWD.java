package asia.nghiango.dbhelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import asia.nghiango.entities.Entity;
import asia.nghiango.model.Model;

/**
 * PlaintextDWD
 */
public class InMemoryDWD implements DataWriterDriver {
    private Dictionary<String, Dictionary<Integer, Entity>> database = new Hashtable<>();
    private Integer currentId = 1;

    private Dictionary<Integer, Entity> getTable(String tableName) {
        Dictionary<Integer, Entity> table;
        table = this.database.get(tableName);
        if (table == null){
            table = new Hashtable<>();
            this.database.put(tableName, table);
        }
        return table;
    }

    @Override
    public List<Entity> getAll(String tableName, List<String> colNames) {
        Dictionary<Integer, Entity> table = getTable(tableName);

        List<Entity> arrLst = new ArrayList<Entity>();

        Enumeration<Integer> keys = table.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            Entity element = table.get(key);
            if (!element.isRemoved()) {
                arrLst.add(element);
            }
        }

        return arrLst;
    }

    @Override
    public Optional<Entity> get(String tableName, List<String> colNames, int id) {
        Dictionary<Integer, Entity> table = getTable(tableName);

        try {
            Entity element = table.get(id);

            if (element.isRemoved()) {
                return Optional.ofNullable(null);
            }

            return Optional.of(element);
        } catch (NullPointerException e) {
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Entity save(Model t) {
        Entity result = new Entity(this.currentId, t);

        Dictionary<Integer, Entity> table = getTable(t.getName());
        table.put(this.currentId, result);
        this.currentId += 1;
        return result;
    }

    @Override
    public void update(Entity t) {
        Dictionary<Integer, Entity> table = getTable(t.getTableName());
        table.put(t.getId(), t);
    }

    @Override
    public void delete(Entity t) {
        t.remove();
    }

    @Override
    public void prepared() {
    }

}
