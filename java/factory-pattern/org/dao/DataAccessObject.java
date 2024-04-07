package org.dao;

import java.util.List;
import java.util.Optional;
import org.entities.Entities;

/**
 * DataAccessObject
 */
public interface DataAccessObject<T> {
    public List<Entities<T>> getAll();

    public Optional<Entities<T>> get(int id);

    public Entities<T> save(T t);

    public void update(Entities<T> t);

    public void delete(Entities<T> t);
}
