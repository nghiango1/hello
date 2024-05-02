package asia.nghiango.utilities;

import java.time.LocalDateTime;

import asia.nghiango.model.PageVisitRecord;

import java.sql.Timestamp;

/**
 * Util
 */
public class Util {

    public static Timestamp dateNow() {
        LocalDateTime t = java.time.LocalDateTime.now();
        return Timestamp.valueOf(t);
    }

    public static PageVisitRecord dummyWebVisitRecordData() {
        Timestamp requestTime = Util.dateNow();
        Timestamp serveTime = Util.dateNow();
        Timestamp leaveTime = Util.dateNow();
        PageVisitRecord stat = new PageVisitRecord(
                "https://nghiango.asia",
                "/",
                requestTime,
                serveTime,
                leaveTime,
                "https://google.com",
                "chrome",
                "pc",
                "linux");
        return stat;
    }
}
