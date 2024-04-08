package org.dbhelper;

import java.util.List;
import java.util.Optional;

import org.entities.Entities;

/**
 * PlaintextDWD
 */
public class PlaintextDWD<T> implements DataWriterDriver<T> {
    private String filePath;

    public PlaintextDWD(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Entities<T>> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public Optional<Entities<T>> get(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Entities<T> save(T t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Entities<T> t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Entities<T> id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
