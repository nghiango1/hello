import java.sql.Timestamp;
import java.util.Optional;

import org.dao.*;
import org.entities.Entities;
import org.model.WebAnalyticStat;

public class Main {
    public static void main(String[] args) {
        WebAnalyticStatDAO wasDao = new WebAnalyticStatDAO();

        WebAnalyticStat stat = new WebAnalyticStat("https://nghiango.asia", "/", new Timestamp(11), new Timestamp(14),
                new Timestamp(11), "https://google.com", "chrome", "pc", "linux");

        // id: 0
        wasDao.save(stat);
        // id: 1
        Entities<WebAnalyticStat> element2 = wasDao.save(stat);
        // id: 2
        wasDao.save(stat);
        for (Entities<WebAnalyticStat> wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        };

        wasDao.delete(element2);
        for (Entities<WebAnalyticStat> wasEntities : wasDao.getAll()) {
            System.out.println(wasEntities);
        };

        Optional<Entities<WebAnalyticStat>> getElmt2 =wasDao.get(1); // 0-index
        if (getElmt2.isEmpty()) {
            System.out.println("Element 2 not found!");
        }
    }
}
