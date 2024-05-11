package asia.nghiango.model;

import java.sql.Timestamp;

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

    public String toString() {
        return String.format("{pageURL: %s, path: %s, request: %s, ...}", this.pageURL, this.path,
                this.request.toString());
    }
}
