package asia.nghiango.dbhelper;

import java.util.List;
import java.util.Optional;

import asia.nghiango.entities.Entity;
import asia.nghiango.model.Model;

/**
 * PlaintextDWD
 */
public class PlaintextDWD implements DataWriterDriver {
    private String filePath;

    public PlaintextDWD(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Entity> getAll(String tableName, List<String> colNames) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public Optional<Entity> get(String tableName, List<String> colNames, int id) {
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

    @Override
    public void prepared() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prepared'");
    }

}
