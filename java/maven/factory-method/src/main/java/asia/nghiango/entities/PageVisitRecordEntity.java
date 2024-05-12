package asia.nghiango.entities;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import asia.nghiango.dbhelper.DataField;
import asia.nghiango.model.PageVisitRecord;

/**
 * PageVisitRecordEntity
 */
public class PageVisitRecordEntity extends Entity<PageVisitRecord> {
    private static DataField[] dataFields = {
            new DataField("PAGE_URL", JDBCType.VARCHAR),
            new DataField("PAGE_PATH", JDBCType.VARCHAR),
            new DataField("TIME_REQUEST", JDBCType.TIMESTAMP),
            new DataField("TIME_SERVE", JDBCType.TIMESTAMP),
            new DataField("TIME_LEAVE", JDBCType.TIMESTAMP),
            new DataField("REFERER", JDBCType.VARCHAR),
            new DataField("BROWSER", JDBCType.VARCHAR),
            new DataField("DEVICE_TYPE", JDBCType.VARCHAR),
            new DataField("OPERATING_SYSTEM", JDBCType.VARCHAR),
    };

    public PageVisitRecordEntity(Integer id, Boolean isDelete, PageVisitRecord t) {
        super(id, isDelete, t);
    }

    public PageVisitRecordEntity(ResultSet rs) throws SQLException {
        super(rs);
    }

    @Override
    public PageVisitRecord readData(ResultSet rs) throws SQLException {
        String pageURL = rs.getString("PAGE_URL");
        String path = rs.getString("PAGE_PATH");
        Timestamp request = rs.getTimestamp("TIME_REQUEST");
        Timestamp serve = rs.getTimestamp("TIME_SERVE");
        Timestamp leave = rs.getTimestamp("TIME_LEAVE");
        String referer = rs.getString("REFERER");
        String browser = rs.getString("BROWSER");
        String deviceType = rs.getString("DEVICE_TYPE");
        String operatingSystem = rs.getString("OPERATING_SYSTEM");
        return new PageVisitRecord(pageURL, path, request, serve, leave, referer, browser, deviceType, operatingSystem);
    }

    public static String getTableName() {
        return "record";
    }

    @Override
    public Dictionary<String, String> convertDataToDict() {
        Dictionary<String, String> dict = new Hashtable<String, String>();
        dict.put("PAGE_URL", this.data.pageURL.toString());
        dict.put("PAGE_PATH", this.data.path.toString());
        dict.put("TIME_REQUEST", this.data.request.toString());
        dict.put("TIME_SERVE", this.data.serve.toString());
        dict.put("TIME_LEAVE", this.data.leave.toString());
        dict.put("REFERER", this.data.referer.toString());
        dict.put("BROWSER", this.data.browser.toString());
        dict.put("DEVICE_TYPE", this.data.deviceType.toString());
        dict.put("OPERATING_SYSTEM", this.data.operatingSystem.toString());
        return dict;
    }

    public static List<DataField> getDataFields() {
        return Arrays.asList(dataFields);
    }

    public static List<DataField> getColumnNames() {
        List<DataField> colname = new ArrayList<DataField>();
        colname.addAll(getBaseDataFields());
        colname.addAll(getDataFields());

        return colname;
    }
}
