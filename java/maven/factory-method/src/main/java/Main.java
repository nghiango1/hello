import java.util.Optional;

import asia.nghiango.dao.*;
import asia.nghiango.dbhelper.DatabaseHandlerFactory;
import asia.nghiango.dbhelper.DatabaseHandler;
import asia.nghiango.dbhelper.DatabaseHandlerFactory.DatabaseType;
import asia.nghiango.entities.Entity;
import asia.nghiango.model.PageVisitRecord;
import asia.nghiango.utilities.Util;

public class Main {

    public static void main(String[] args) {
        Optional<DatabaseHandler> odwd = DatabaseHandlerFactory.createDWD(DatabaseType.IN_MEM);

        DatabaseHandler dwd;
        if (odwd.isEmpty()) {
            System.out.println("Can't create Data driver, fall back to Inmemory dwd");

            dwd = DatabaseHandlerFactory.createInMemDWD();
        } else {
            dwd = odwd.get();
        }

        WebAnalyticStatDAO wasDao = new WebAnalyticStatDAO(dwd);
        PageVisitRecord stat = Util.dummyWebVisitRecordData();

        // id: 1
        wasDao.save(stat);
        // id: 2
        Entity element2 = wasDao.save(stat);
        // id: 3
        wasDao.save(stat);
        for (Entity wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        }

        wasDao.delete(element2);
        for (Entity wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        }

        Optional<Entity> getElmt2 = wasDao.get(2);
        if (getElmt2.isEmpty()) {
            System.out.println("Element 2 not found!");
        }
    }
}
