import java.util.Optional;

import asia.nghiango.dao.*;
import asia.nghiango.dbhelper.DatabaseHandlerFactory;
import asia.nghiango.dbhelper.DatabaseHandler;
import asia.nghiango.dbhelper.DatabaseHandlerFactory.DatabaseType;
import asia.nghiango.entities.Entity;
import asia.nghiango.entities.PageVisitRecordEntity;
import asia.nghiango.model.PageVisitRecord;
import asia.nghiango.utilities.Util;

public class Main {

    public static void main(String[] args) {
        Optional<DatabaseHandler> optionalDBhandlerInstance = DatabaseHandlerFactory.create(DatabaseType.PostgreSQL);

        DatabaseHandler databaseHandlerInstance;
        if (optionalDBhandlerInstance.isEmpty()) {
            System.out.println("Can't create DB Handler, fall back to In-memory Datastore");

            databaseHandlerInstance = DatabaseHandlerFactory.createInmemoryDatastore();
        } else {
            databaseHandlerInstance = optionalDBhandlerInstance.get();
        }

        PageVisitRecordDAO pvrDaoInstance = new PageVisitRecordDAO(databaseHandlerInstance);
        PageVisitRecord dummyRecord = Util.dummyWebVisitRecordData();

        // id: 1
        pvrDaoInstance.save(dummyRecord);
        // id: 2
        Entity element2 = pvrDaoInstance.save(dummyRecord);
        // id: 3
        pvrDaoInstance.save(dummyRecord);
        for (PageVisitRecordEntity wasEntities : pvrDaoInstance.getAll()) {
            System.out.println(wasEntities);
        }

        pvrDaoInstance.delete(element2);
        for (Entity wasEntities : pvrDaoInstance.getAll()) {
            System.out.println(wasEntities);
        }

        Optional<PageVisitRecordEntity> getElmt2 = pvrDaoInstance.get(2);
        if (getElmt2.isEmpty()) {
            System.out.println("Element 2 not found!");
        }
    }
}
