package asia.nghiango.entities;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import asia.nghiango.dbhelper.DataField;
import asia.nghiango.model.PageVisitRecord;

/**
 * PageVisitRecordEntity
 */
public class PageVisitRecordEntity extends Entity<PageVisitRecord> {
    private static enum Fields {
        PAGE_URL(new DataField("PAGE_URL", JDBCType.VARCHAR)),
        PAGE_PATH(new DataField("PAGE_PATH", JDBCType.VARCHAR)),
        TIME_REQUEST(new DataField("TIME_REQUEST", JDBCType.TIMESTAMP)),
        TIME_SERVE(new DataField("TIME_SERVE", JDBCType.TIMESTAMP)),
        TIME_LEAVE(new DataField("TIME_LEAVE", JDBCType.TIMESTAMP)),
        REFERER(new DataField("REFERER", JDBCType.VARCHAR)),
        BROWSER(new DataField("BROWSER", JDBCType.VARCHAR)),
        DEVICE_TYPE(new DataField("DEVICE_TYPE", JDBCType.VARCHAR)),
        OPERATING_SYSTEM(new DataField("OPERATING_SYSTEM", JDBCType.VARCHAR));

        private final DataField info;

        private Fields(DataField df) {
            this.info = df;
        }

        public static List<DataField> getAllFieldsInfo() {
            Fields[] fs = Fields.values();
            List<DataField> df = new ArrayList<DataField>();
            for (Fields f : fs) {
                df.add(f.getInfo());
            }
            return df;
        }

        public final DataField getInfo() {
            return this.info;
        }

        public final String getName() {
            return this.info.name;
        }

        public final JDBCType getType() {
            return this.info.type;
        }
    };

    public PageVisitRecordEntity(Integer id, Boolean isDelete, PageVisitRecord t) {
        super(id, isDelete, t);
    }

    public PageVisitRecordEntity(ResultSet rs) throws SQLException {
        super(rs);
    }

    @Override
    public PageVisitRecord readData(ResultSet rs) throws SQLException {
        String pageURL = rs.getString(Fields.PAGE_URL.getName());
        String path = rs.getString(Fields.PAGE_PATH.getName());
        Timestamp request = rs.getTimestamp(Fields.TIME_REQUEST.getName());
        Timestamp serve = rs.getTimestamp(Fields.TIME_SERVE.getName());
        Timestamp leave = rs.getTimestamp(Fields.TIME_LEAVE.getName());
        String referer = rs.getString(Fields.REFERER.getName());
        String browser = rs.getString(Fields.BROWSER.getName());
        String deviceType = rs.getString(Fields.DEVICE_TYPE.getName());
        String operatingSystem = rs.getString(Fields.OPERATING_SYSTEM.getName());
        return new PageVisitRecord(pageURL, path, request, serve, leave, referer, browser, deviceType, operatingSystem);
    }

    public static String getTableName() {
        return "record";
    }

    @Override
    public Dictionary<DataField, String> convertDataToDict() {
        Dictionary<DataField, String> dict = new Hashtable<DataField, String>();
        dict.put(Fields.PAGE_URL.getInfo(), this.data.pageURL.toString());
        dict.put(Fields.PAGE_PATH.getInfo(), this.data.path.toString());
        dict.put(Fields.TIME_REQUEST.getInfo(), this.data.request.toString());
        dict.put(Fields.TIME_SERVE.getInfo(), this.data.serve.toString());
        dict.put(Fields.TIME_LEAVE.getInfo(), this.data.leave.toString());
        dict.put(Fields.REFERER.getInfo(), this.data.referer.toString());
        dict.put(Fields.BROWSER.getInfo(), this.data.browser.toString());
        dict.put(Fields.DEVICE_TYPE.getInfo(), this.data.deviceType.toString());
        dict.put(Fields.OPERATING_SYSTEM.getInfo(), this.data.operatingSystem.toString());
        return dict;
    }

    public static List<DataField> getDataFields() {
        return Fields.getAllFieldsInfo();
    }

    public static List<DataField> getColumnNames() {
        List<DataField> colname = new ArrayList<DataField>();
        colname.addAll(getBaseDataFields());
        colname.addAll(getDataFields());

        return colname;
    }
}
