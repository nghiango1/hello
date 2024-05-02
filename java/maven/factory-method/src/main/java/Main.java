import java.util.Optional;

import asia.nghiango.dao.*;
import asia.nghiango.dbhelper.DWDFactory;
import asia.nghiango.dbhelper.DataWriterDriver;
import asia.nghiango.dbhelper.DWDFactory.DWDType;
import asia.nghiango.entities.Entity;
import asia.nghiango.model.WebAnalyticStat;
import asia.nghiango.utilities.Util;

public class Main {

    public static void main(String[] args) {
        DWDFactory DWDFactoryInstance = new DWDFactory();
        Optional<DataWriterDriver> odwd = DWDFactoryInstance.createDWD(DWDType.MYSQL);

        DataWriterDriver dwd;
        if (odwd.isEmpty()) {
            System.out.println("Can't create Data driver, fall back to Inmemory dwd");

            dwd = DWDFactoryInstance.createInMemDWD();
        } else {
            dwd = odwd.get();
        }

        WebAnalyticStatDAO wasDao = new WebAnalyticStatDAO(dwd);
        WebAnalyticStat stat = Util.dummyWebVisitRecordData();

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
