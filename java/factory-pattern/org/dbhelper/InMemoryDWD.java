package org.dbhelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import org.entities.Entities;

/**
 * PlaintextDWD
 */
public class InMemoryDWD<T> implements DataWriterDriver<T> {
    private Dictionary<Integer, Entities<T>> data = new Hashtable<Integer, Entities<T>>();
    private Integer currentId = 0;

    @Override
    public List<Entities<T>> getAll() {
        List<Entities<T>> arrLst = new ArrayList<Entities<T>>();

        Enumeration<Integer> keys = this.data.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            Entities<T> element = data.get(key);
            if (!element.isRemoved()) {
                arrLst.add(element);
            }
        }

        return arrLst;
    }

    @Override
    public Optional<Entities<T>> get(int id) {
        try {
            Entities<T> element = data.get(id);

            if (element.isRemoved()) {
                return Optional.ofNullable(null);
            }

            return Optional.of(element);
        } catch (NullPointerException e) {
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Entities<T> save(T t) {
        Entities<T> result = new Entities<T>(this.currentId, t);
        this.data.put(this.currentId, result);
        this.currentId += 1;
        return result;
    }

    @Override
    public void update(Entities<T> t) {
        this.data.put(t.getId(), t);
    }

    @Override
    public void delete(Entities<T> t) {
        t.remove();
    }

}
