package asia.nghiango.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import asia.nghiango.dbhelper.DataWriterDriver;
import asia.nghiango.dbhelper.InMemoryDWD;
import asia.nghiango.entities.Entity;
import asia.nghiango.model.PageVisitRecord;

/**
 * 
 */
public class WebAnalyticStatDAO implements DataAccessObject<PageVisitRecord> {
    private DataWriterDriver driver;

    public WebAnalyticStatDAO() {
        this.driver = new InMemoryDWD();
    }

    public WebAnalyticStatDAO(DataWriterDriver driver) {
        this.driver = driver;
    }

    public List<Entity> getAll() {
        List<String> colNames = new ArrayList<String>();
        colNames.addAll(Entity.getColumnNames());
        colNames.addAll(PageVisitRecord.getColumnNames());
        return this.driver.getAll(PageVisitRecord.getTableNames(), colNames);
    };

    public Optional<Entity> get(int id) {
        return this.driver.get(PageVisitRecord.getTableNames(), PageVisitRecord.getColumnNames(), id);
    };

    public Entity save(PageVisitRecord t) {
        return this.driver.save(t);
    };

    public void update(Entity t) {
        this.driver.update(t);
    };

    public void delete(Entity t) {
        this.driver.delete(t);
    };
}
