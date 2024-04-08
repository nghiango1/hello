import java.sql.Timestamp;
import java.util.Optional;

import org.dao.*;
import org.dbhelper.DWDFactory;
import org.dbhelper.DataWriterDriver;
import org.dbhelper.DWDFactory.DWDType;
import org.entities.Entities;
import org.model.WebAnalyticStat;

public class Main {
    public static void main(String[] args) {
        DWDFactory<WebAnalyticStat> DWDFactoryInstance = new DWDFactory<WebAnalyticStat>();
        DataWriterDriver<WebAnalyticStat> dwd = DWDFactoryInstance.createDWD(DWDType.IN_MEM);
        WebAnalyticStatDAO wasDao = new WebAnalyticStatDAO(dwd);

        Timestamp requestTime = new Timestamp(11000);
        Timestamp serveTime = new Timestamp(14000);
        Timestamp leaveTime = new Timestamp(18000);
        WebAnalyticStat stat = new WebAnalyticStat(
                "https://nghiango.asia",
                "/",
                requestTime,
                serveTime,
                leaveTime,
                "https://google.com",
                "chrome",
                "pc",
                "linux");

        // id: 0
        wasDao.save(stat);
        // id: 1
        Entities<WebAnalyticStat> element2 = wasDao.save(stat);
        // id: 2
        wasDao.save(stat);
        for (Entities<WebAnalyticStat> wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        }
        ;

        wasDao.delete(element2);
        for (Entities<WebAnalyticStat> wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        }
        ;

        Optional<Entities<WebAnalyticStat>> getElmt2 = wasDao.get(1); // 0-index
        if (getElmt2.isEmpty()) {
            System.out.println("Element 2 not found!");
        }
    }
}
