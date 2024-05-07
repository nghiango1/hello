package asia.nghiango.dbhelper;

import java.util.List;
import java.util.Optional;
import asia.nghiango.entities.Entity;
import asia.nghiango.model.Model;

/**
 * DataWriterDriver
 */
public interface DatabaseHandler {
    public List<? extends Entity> getAll(String tableName, List<String> colNames);

    public Optional<? extends Entity> get(String tableName, List<String> colNames, int id);

    /**
     * Save the data and return that element id
     *
     * @param t
     */
    public Entity save(Model t);

    public void update(Entity t);

    public void delete(Entity id);
}
