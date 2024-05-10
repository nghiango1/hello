package asia.nghiango.dao;

import java.util.List;
import java.util.Optional;
import asia.nghiango.entities.Entity;

/**
 * DataAccessObject
 */
public interface DataAccessObject<T> {
    /**
     * Prepare - create the table in the database
     */
    public void prepared();

    public List<? extends Entity> getAll();

    public Optional<? extends Entity> get(int id);

    public Entity save(T t);

    public void update(Entity t);

    public void delete(Entity t);
}
