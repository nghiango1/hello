package org.entities;

/**
 * WebAnalyticStatEntities
 */
public class Entities<T> {
    private final Integer id;
    private Boolean isDeleted = false;
    private T data;

    public Entities(Integer id, T t) {
        this.data = t;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public T getData() {
        return this.data;
    }

    public Boolean isRemoved() {
        return this.isDeleted;
    }

    public void remove() {
        this.isDeleted = true;
    }

    public String toString() {
        return String.format("{id: %d, data: %s}", this.id, this.data.toString());
    }
}
