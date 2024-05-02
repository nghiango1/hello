package asia.nghiango.entities;

import java.util.Optional;

import asia.nghiango.model.Model;
import asia.nghiango.model.PageVisitRecord;

/**
 * EntityFactory
 */
public class EntityFactory {

    public static Optional<Entity> create(Integer id, String name, Model model) {
        Class<? extends Model> c = model.getClass();
        System.out.println(c.toString());
        
        if (model.getName() == "PageVisitRecord") {
            return Optional.of(new PageVisitRecordEntity(id, (PageVisitRecord) model));
        }
        
        return Optional.ofNullable(null);
    }
}
