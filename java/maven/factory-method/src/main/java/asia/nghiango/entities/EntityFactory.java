package asia.nghiango.entities;

import java.util.Optional;

import asia.nghiango.model.Model;
import asia.nghiango.model.PageVisitRecord;

/**
 * EntityFactory
 */
public class EntityFactory {

    public static enum SupportedType {
        PageVisitRecord,
    }

    public static Optional<Entity> create(Integer id, SupportedType type, Model model) {
        if (type == SupportedType.PageVisitRecord) {
            return Optional.of(new PageVisitRecordEntity(id, (PageVisitRecord) model));
        }
        
        return Optional.ofNullable(null);
    }
}
