package asia.nghiango.entities;

import java.sql.ResultSet;
import java.util.Optional;

import asia.nghiango.model.Model;
import asia.nghiango.model.PageVisitRecord;

/**
 * PageVisitRecordEntity
 */
public class PageVisitRecordEntity extends Entity{
    private PageVisitRecord data;

    public PageVisitRecordEntity(Integer id, PageVisitRecord t) {
        super(id, t);
        this.data = t;
    }
}
