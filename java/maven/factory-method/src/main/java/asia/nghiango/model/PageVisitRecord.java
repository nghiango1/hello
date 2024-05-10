package asia.nghiango.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import asia.nghiango.entities.EntityFactory.SupportedType;

/*
 * PageVisitRecord modelling infomation of a web trafic needed for analystic
 */
public class PageVisitRecord implements Model {
    public String pageURL;
    public String path;

    public Timestamp request;
    public Timestamp serve;
    public Timestamp leave;

    public String referer;
    public String browser;
    public String deviceType;
    public String operatingSystem;

    /**
     * Construct an empty PageVisitRecord object that you can set all value manually
     * later, value will be set as default
     */
    public PageVisitRecord() {
        this.pageURL = "";
        this.path = "";

        this.request = new Timestamp(System.currentTimeMillis());
        this.serve = new Timestamp(System.currentTimeMillis());
        this.leave = new Timestamp(System.currentTimeMillis());

        this.referer = "";
        this.browser = "";
        this.deviceType = "";
        this.operatingSystem = "";
    }

    public PageVisitRecord(
            String pageURL, String path, Timestamp request, Timestamp serve, Timestamp leave, String referer,
            String browser, String deviceType, String operatingSystem) {
        this.pageURL = pageURL;
        this.path = path;

        this.request = request;
        this.serve = serve;
        this.leave = leave;

        this.referer = referer;
        this.browser = browser;
        this.deviceType = deviceType;
        this.operatingSystem = operatingSystem;
    }

    private static String[] columnNames = {
            "PAGE_URL",
            "PAGE_PATH",
            "TIME_REQUEST",
            "TIME_SERVE",
            "TIME_LEAVE",
            "REFERER",
            "BROWSER",
            "DEVICE_TYPE",
            "OPERATING_SYSTEM"
    };

    public PageVisitRecord(ResultSet rs) {
        try {
            this.pageURL = rs.getString("PAGE_URL");
            this.path = rs.getString("PAGE_PATH");
            this.request = rs.getTimestamp("TIME_REQUEST");
            this.serve = rs.getTimestamp("TIME_SERVE");
            this.leave = rs.getTimestamp("TIME_LEAVE");
            this.referer = rs.getString("REFERER");
            this.browser = rs.getString("BROWSER");
            this.deviceType = rs.getString("DEVICE_TYPE");
            this.operatingSystem = rs.getString("OPERATING_SYSTEM");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public String toString() {
        return String.format("{pageURL: %s, path: %s, request: %s, ...}", this.pageURL, this.path,
                this.request.toString());
    }

    @Override
    public Model convertRowToModel(ResultSet rs) {
        PageVisitRecord t = new PageVisitRecord(rs);
        return t;
    }

    @Override
    public Dictionary<String, String> convertDict() {
        Dictionary<String, String> rs = new Hashtable<String, String>();
        rs.put(PageVisitRecord.columnNames[0], this.pageURL);
        rs.put(PageVisitRecord.columnNames[1], this.path);
        rs.put(PageVisitRecord.columnNames[2], this.request.toString());
        rs.put(PageVisitRecord.columnNames[3], this.serve.toString());
        rs.put(PageVisitRecord.columnNames[4], this.leave.toString());
        rs.put(PageVisitRecord.columnNames[5], this.referer);
        rs.put(PageVisitRecord.columnNames[6], this.browser);
        rs.put(PageVisitRecord.columnNames[7], this.deviceType);
        rs.put(PageVisitRecord.columnNames[8], this.operatingSystem);
        return rs;
    }

    @Override
    public void setDataFromRow(ResultSet rs) {
        try {
            this.pageURL = rs.getString("PAGE_URL");
            this.path = rs.getString("PAGE_PATH");
            this.request = rs.getTimestamp("TIME_REQUEST");
            this.serve = rs.getTimestamp("TIME_SERVE");
            this.leave = rs.getTimestamp("TIME_LEAVE");
            this.referer = rs.getString("REFERER");
            this.browser = rs.getString("BROWSER");
            this.deviceType = rs.getString("DEVICE_TYPE");
            this.operatingSystem = rs.getString("OPERATING_SYSTEM");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String getTableNames() {
        return "record";
    }

    public static List<String> getColumnNames() {
        return Arrays.asList(columnNames);
    }

    @Override
    public SupportedType getModelType() {
        return SupportedType.PageVisitRecord;
    }

    /**
     * Tell the name of the Model, this is different from SQL table name
     *
     * @return 
     */
    @Override
    public String getName() {
        return "PageVisitRecord";
    }
}
