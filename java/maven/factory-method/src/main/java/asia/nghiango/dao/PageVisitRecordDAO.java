package asia.nghiango.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import asia.nghiango.dbhelper.DatabaseHandler;
import asia.nghiango.dbhelper.InMemoryDatabaseHandler;
import asia.nghiango.entities.Entity;
import asia.nghiango.entities.PageVisitRecordEntity;
import asia.nghiango.model.PageVisitRecord;

/**
 * 
 */
public class PageVisitRecordDAO implements DataAccessObject<PageVisitRecord> {
    private DatabaseHandler driver;

    public PageVisitRecordDAO() {
        this.driver = new InMemoryDatabaseHandler();
    }

    public PageVisitRecordDAO(DatabaseHandler driver) {
        this.driver = driver;
    }

    @SuppressWarnings("unchecked")
    public List<PageVisitRecordEntity> getAll() {
        List<String> colNames = new ArrayList<String>();
        colNames.addAll(Entity.getColumnNames());
        colNames.addAll(PageVisitRecord.getColumnNames());
        return (List<PageVisitRecordEntity>) this.driver.getAll(PageVisitRecord.getTableNames(), colNames);
    };

    @SuppressWarnings("unchecked")
    public Optional<PageVisitRecordEntity> get(int id) {
        return (Optional<PageVisitRecordEntity>) this.driver.get(PageVisitRecord.getTableNames(), PageVisitRecord.getColumnNames(), id);
    };

    public Entity save(PageVisitRecord t) {
        return this.driver.save(t);
    };

    public void update(Entity t) {
        this.driver.update(t);
    };

    public void delete(Entity t) {
        this.driver.delete(t);
    }

	@Override
	public void prepared() {
	};
}
