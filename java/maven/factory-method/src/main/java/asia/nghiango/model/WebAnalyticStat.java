package asia.nghiango.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/*
 * WebAnalyticStat modelling infomation of a web trafic needed for analystic
 */
public class WebAnalyticStat implements Model {
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
     * Construct an empty WebAnalyticStat object that you can set all value manually
     * later, value will be set as default
     */
    public WebAnalyticStat() {
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

    public WebAnalyticStat(
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

    public WebAnalyticStat(ResultSet rs) {
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
        WebAnalyticStat t = new WebAnalyticStat(rs);
        return t;
    }

    @Override
    public Dictionary<String, String> convertDict() {
        Dictionary<String, String> rs = new Hashtable<String, String>();
        rs.put(WebAnalyticStat.columnNames[0], this.pageURL);
        rs.put(WebAnalyticStat.columnNames[1], this.path);
        rs.put(WebAnalyticStat.columnNames[2], this.request.toString());
        rs.put(WebAnalyticStat.columnNames[3], this.serve.toString());
        rs.put(WebAnalyticStat.columnNames[4], this.leave.toString());
        rs.put(WebAnalyticStat.columnNames[5], this.referer);
        rs.put(WebAnalyticStat.columnNames[6], this.browser);
        rs.put(WebAnalyticStat.columnNames[7], this.deviceType);
        rs.put(WebAnalyticStat.columnNames[8], this.operatingSystem);
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

    // MySQL create table sql command
    public static String createTableMySQLCommand() {
        return """
                CREATE TABLE `record` (
                  `ID` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
                  `IS_DELETE` tinyint(1) unsigned NOT NULL DEFAULT '0',
                  `PAGE_URL` varchar(50) COLLATE 'utf8mb4_vietnamese_ci' NOT NULL,
                  `PAGE_PATH` varchar(50) NOT NULL,
                  `TIME_REQUEST` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                  `TIME_SERVE` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                  `TIME_LEAVE` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                  `REFERER` varchar(50) COLLATE 'utf8mb4_vietnamese_ci' NOT NULL,
                  `BROWSER` varchar(50) COLLATE 'utf8mb4_vietnamese_ci' NOT NULL,
                  `DEVICE_TYPE` varchar(50) COLLATE 'utf8mb4_vietnamese_ci' NOT NULL,
                  `OPERATING_SYSTEM` varchar(50) COLLATE 'utf8mb4_vietnamese_ci' NOT NULL
                ) ENGINE='InnoDB' COLLATE 'utf8mb4_vietnamese_ci' AUTO_INCREMENT=1;
                """;
    }

    // PostgreSQL create table sql command
    public static String createTablePostgreSQLCommand() {
        return """
                CREATE TABLE "record" (
                  "ID" serial NOT NULL,
                  PRIMARY KEY ("ID"),
                  "IS_DELETE" smallint NOT NULL DEFAULT '0',
                  "PAGE_URL" character varying(50) NOT NULL,
                  "PAGE_PATH" character varying(50) NOT NULL,
                  "TIME_REQUEST" timestamp NOT NULL,
                  "TIME_SERVE" timestamp NOT NULL,
                  "TIME_LEAVE" timestamp NOT NULL,
                  "REFERER" character varying(50) NOT NULL,
                  "BROWSER" character varying(50) NOT NULL,
                  "DEVICE_TYPE" character varying(50) NOT NULL,
                  "OPERATING_SYSTEM" character varying(50) NOT NULL
                );
                """;
    }

    @Override
    public String getName() {
        return "record";
    }
}
