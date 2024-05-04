import java.lang.System.Logger.Level;
import java.util.Optional;

import asia.nghiango.dao.*;
import asia.nghiango.dbhelper.DatabaseHandlerFactory;
import asia.nghiango.dbhelper.DatabaseHandler;
import asia.nghiango.entities.Entity;
import asia.nghiango.entities.PageVisitRecordEntity;
import asia.nghiango.model.PageVisitRecord;
import asia.nghiango.utilities.Env;
import asia.nghiango.utilities.Log;
import asia.nghiango.utilities.Util;

public class Main {

    public static void init() {
        Env.readEnv();
    }

    public static void main(String[] args) {
        init();

        Optional<DatabaseHandler> optionalDBhandlerInstance = DatabaseHandlerFactory.create(Env.getDatabaseType());

        DatabaseHandler databaseHandlerInstance;
        if (optionalDBhandlerInstance.isEmpty()) {
            Log.printLog(Level.WARNING, "Can't create DB Handler, fall back to In-memory Datastore");

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
        for (PageVisitRecordEntity pvrEntity : pvrDaoInstance.getAll()) {
            Log.printLog(Level.INFO, pvrEntity.toString());
        }

        pvrDaoInstance.delete(element2);
        for (Entity entity : pvrDaoInstance.getAll()) {
            Log.printLog(Level.INFO, entity.toString());
        }

        Optional<PageVisitRecordEntity> getElmt2 = pvrDaoInstance.get(2);
        if (getElmt2.isEmpty()) {
            Log.printLog(Level.INFO, "Element 2 not found!");
        }
    }
}
