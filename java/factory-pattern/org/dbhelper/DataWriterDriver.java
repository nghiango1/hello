package org.dbhelper;

import java.util.List;
import java.util.Optional;
import org.entities.Entities;

/**
 * DataWriterDriver
 */
public interface DataWriterDriver<T> {
    public List<Entities<T>> getAll();

    public Optional<Entities<T>> get(int id);

    /**
     * Save the data and return that element id
     *
     * @param t
     */
    public Entities<T> save(T t);

    public void update(Entities<T> t);

    public void delete(Entities<T> id);
}
