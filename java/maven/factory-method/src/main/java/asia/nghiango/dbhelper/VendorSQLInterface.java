package asia.nghiango.dbhelper;

/**
 * VendorSQLInterface
 */
public interface VendorSQLInterface {

    public String vendorTableNameHandler(String tableName);

    public String vendorFieldNameHandler(DataField dataField);

    public String vendorValueHandler(DataField dataField, String value);

}
