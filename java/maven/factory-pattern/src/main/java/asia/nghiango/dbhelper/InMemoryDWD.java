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
    private Dictionary<Integer, Entity> data = new Hashtable<Integer, Entity>();
    private Integer currentId = 0;

    @Override
    public List<Entity> getAll() {
        List<Entity> arrLst = new ArrayList<Entity>();

        Enumeration<Integer> keys = this.data.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            Entity element = data.get(key);
            if (!element.isRemoved()) {
                arrLst.add(element);
            }
        }

        return arrLst;
    }

    @Override
    public Optional<Entity> get(int id) {
        try {
            Entity element = data.get(id);

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
        this.data.put(this.currentId, result);
        this.currentId += 1;
        return result;
    }

    @Override
    public void update(Entity t) {
        this.data.put(t.getId(), t);
    }

    @Override
    public void delete(Entity t) {
        t.remove();
    }

}
