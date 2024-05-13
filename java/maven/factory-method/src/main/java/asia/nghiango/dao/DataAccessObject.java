package asia.nghiango.dao;

import java.util.List;
import java.util.Optional;
import asia.nghiango.entities.Entity;
import asia.nghiango.model.Model;

/**
 * DataAccessObject
 */
public interface DataAccessObject<T extends Model, K extends Entity<T>> {
    /**
     * Prepare - create the table in the database
     */
    public void prepared();

    public Optional<List<K>> getAll();

    public Optional<K> get(Integer id);

    public K save(T t);

    public void update(K t);

    public void delete(K t);

    public String constructInsertStatement(K entity);

    public String constructUpdateStatement(K entity);
}
