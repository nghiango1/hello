package org.model;

import java.sql.Timestamp;

/*
 * WebAnalyticStat modelling infomation of a web trafic needed for analystic
 */
public class WebAnalyticStat {
    public String pageURL;
    public String path;

    public Timestamp request;
    public Timestamp serve;
    public Timestamp leave;

    public String referer;
    public String browser;
    public String deviceType;
    public String operatingSystem;

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

    public String toString() {
        return String.format("{pageURL: %s, path: %s, request: %s, ...}", this.pageURL, this.path, this.request.toString());
    }
}
