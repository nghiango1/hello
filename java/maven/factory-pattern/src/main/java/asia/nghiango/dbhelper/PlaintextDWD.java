package asia.nghiango.dbhelper;

import java.util.List;
import java.util.Optional;

import asia.nghiango.entities.Entity;
import asia.nghiango.model.Model;

/**
 * PlaintextDWD
 */
public class PlaintextDWD<T> implements DataWriterDriver {
    private String filePath;

    public PlaintextDWD(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Entity> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public Optional<Entity> get(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Entity save(Model t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Entity t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Entity id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
