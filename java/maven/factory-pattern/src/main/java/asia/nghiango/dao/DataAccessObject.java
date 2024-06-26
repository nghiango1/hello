package asia.nghiango.dao;

import java.util.List;
import java.util.Optional;
import asia.nghiango.entities.Entity;

/**
 * DataAccessObject
 */
public interface DataAccessObject<T> {
    public List<Entity> getAll();

    public Optional<Entity> get(int id);

    public Entity save(T t);

    public void update(Entity t);

    public void delete(Entity t);
}
