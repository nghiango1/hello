package asia.nghiango.dao;

import java.lang.System.Logger.Level;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import asia.nghiango.dbhelper.DatabaseHandler;
import asia.nghiango.dbhelper.InMemoryDatabaseHandler;
import asia.nghiango.entities.PageVisitRecordEntity;
import asia.nghiango.model.PageVisitRecord;
import asia.nghiango.utilities.Env;
import asia.nghiango.utilities.Log;

/**
 * PageVisitRecordDAO should have all SQL/Databse related infomation to storing
 * {@link PageVisitRecord} busines object model
 */
public class PageVisitRecordDAO implements DataAccessObject<PageVisitRecord, PageVisitRecordEntity> {
    private DatabaseHandler driver;
    private Integer currentId = 1;

    public PageVisitRecordDAO() {
        this.driver = new InMemoryDatabaseHandler();
    }

    public PageVisitRecordDAO(DatabaseHandler driver) {
        this.driver = driver;
    }

    public Optional<List<PageVisitRecordEntity>> getAll() {
        Optional<ResultSet> rs = driver.getAll(PageVisitRecordEntity.getTableName(),
                PageVisitRecordEntity.getColumnNames());
        if (rs.isEmpty()) {
            return Optional.ofNullable(null);
        }

        List<PageVisitRecordEntity> arrLst = new ArrayList<PageVisitRecordEntity>();

        ResultSet rSet = rs.get();

        try {
            while (rSet.next()) {
                Optional<PageVisitRecordEntity> entity = Optional.ofNullable(null);
                try {
                    entity = Optional.of(new PageVisitRecordEntity(rSet));
                } catch (SQLException ex) {
                    Log.printLog(Level.ERROR,
                            "False to read result set for create PageVisitRecordEntity, got SQLException error: "
                                    + ex.getMessage());

                    Log.printLog(Level.DEBUG, "SQLState: " + ex.getSQLState());
                    Log.printLog(Level.DEBUG, "VendorError: " + ex.getErrorCode());
                    Log.printLog(Level.DEBUG, "Current ResultSet value: " + rSet.toString());
                }

                if (entity.isEmpty()) {
                    Log.printLog(Level.INFO, "Encounter error creating Entity, skip to next line");
                    continue;
                }

                if (entity.get().isDelete()) {
                    Log.printLog(Level.INFO,
                            String.format(
                                    "Entity {id=%d} marked as is deleted and won't be add to getAll() result list",
                                    entity.get().getId()));
                    continue;
                }

                arrLst.add(entity.get());
            }
        } catch (SQLException ex) {
            // handle any errors
            Log.printLog(Level.ERROR, "Fail to read result set, got SQLException error: " + ex.getMessage());

            Log.printLog(Level.DEBUG, "\tSQLState: " + ex.getSQLState());
            Log.printLog(Level.DEBUG, "\tVendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return Optional.of(arrLst);

        // return (List<PageVisitRecordEntity>)
        // this.driver.getAll(PageVisitRecord.getTableNames(), colNames);
    }

    public Optional<PageVisitRecordEntity> get(int id) {
        // return (Optional<PageVisitRecordEntity>)
        // this.driver.get(PageVisitRecordEntity.getTableName(),
        // PageVisitRecordEntity.getBaseColumnNames(), id);
        return Optional.ofNullable(null);
    }

    public PageVisitRecordEntity save(PageVisitRecord t) {
        PageVisitRecordEntity entity = new PageVisitRecordEntity(this.currentId, false, t);
        this.currentId += 1;
        String sqlStmt = driver.constructInsertStatement(entity);
        driver.insert(sqlStmt);
        return entity;
    }

    public void update(PageVisitRecordEntity t) {
        String sqlStmt = driver.constructUpdateStatement(t);
        driver.update(sqlStmt);
    };

    public void delete(PageVisitRecordEntity t) {
        t.remove();
        try {
            update(t);
        } catch (Exception e) {
            Log.printLog(Level.WARNING, "Fail to delete element, got Error:" + e.getMessage());
        }
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
    public void prepared() {
        switch (Env.getDatabaseType()) {
            case FILE:
                throw new UnsupportedOperationException("File storage isn't implemented yet");
            // break;

            case MYSQL:
                this.driver.createTable(createTableMySQLCommand());
                break;

            case POSTGRESQL:
                this.driver.createTable(createTablePostgreSQLCommand());
                break;

            default:
                throw new UnsupportedOperationException("File storage isn't implemented yet");
            // break;
        }
        // this.driver.createTable(tableName, column, extras);
    }
}
