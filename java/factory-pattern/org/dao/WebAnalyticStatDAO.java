package org.dao;

import java.util.List;
import java.util.Optional;

import org.dbhelper.DataWriterDriver;
import org.dbhelper.InMemoryDWD;
import org.entities.Entities;
import org.model.WebAnalyticStat;

/**
 * 
 */
public class WebAnalyticStatDAO implements DataAccessObject<WebAnalyticStat> {
    private DataWriterDriver<WebAnalyticStat> driver; 

    public WebAnalyticStatDAO() {
        this.driver = new InMemoryDWD<WebAnalyticStat>();
    }

    public WebAnalyticStatDAO(DataWriterDriver<WebAnalyticStat> driver) {
        this.driver = driver;
    }

    public List<Entities<WebAnalyticStat>> getAll() {
        return this.driver.getAll();
    };

    public Optional<Entities<WebAnalyticStat>> get(int id) {
        return this.driver.get(id);
    };

    public Entities<WebAnalyticStat> save(WebAnalyticStat t) {
        return this.driver.save(t);
    };

    public void update(Entities<WebAnalyticStat> t) {
        this.driver.update(t);
    };

    public void delete(Entities<WebAnalyticStat> t) {
        this.driver.delete(t);
    };
}
