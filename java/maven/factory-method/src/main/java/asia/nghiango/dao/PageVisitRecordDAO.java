package asia.nghiango.dao;

import java.lang.System.Logger.Level;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import asia.nghiango.dbhelper.DatabaseHandler;
import asia.nghiango.dbhelper.InMemoryDatabaseHandler;
import asia.nghiango.dbhelper.InsertSQLBuilder;
import asia.nghiango.dbhelper.UpdateSQLBuilder;
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

    @Override
    public String constructInsertStatement(PageVisitRecordEntity entity) {
        InsertSQLBuilder builder = new InsertSQLBuilder(driver);
        String sqlStmt = builder.addTableName(PageVisitRecordEntity.getTableName())
                .addFields(PageVisitRecordEntity.getColumnNames())
                .addValue(entity.convertToDictionary())
                .build();
        return sqlStmt;
    }

    @Override
    public String constructUpdateStatement(PageVisitRecordEntity entity) {
        UpdateSQLBuilder builder = new UpdateSQLBuilder(driver);
        String sqlStmt = builder.addTableName(PageVisitRecordEntity.getTableName())
                .addFields(PageVisitRecordEntity.getColumnNames())
                .setValue(entity.convertToDictionary())
                .setUpdateByID(PageVisitRecordEntity.getBaseDataFields().get(0), entity.getId().toString())
                .build();
        return sqlStmt;
    }

    private Optional<List<PageVisitRecordEntity>> convertToEntity(ResultSet rSet) {
        List<PageVisitRecordEntity> arrLst = new ArrayList<PageVisitRecordEntity>();

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
                                    "Entity {id=%d} marked as is deleted and won't be add to converted result list",
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
    }

    public Optional<List<PageVisitRecordEntity>> getAll() {
        Optional<ResultSet> rs = driver.getAll(PageVisitRecordEntity.getTableName(),
                PageVisitRecordEntity.getColumnNames());
        if (rs.isEmpty()) {
            return Optional.ofNullable(null);
        }

        ResultSet rSet = rs.get();

        return this.convertToEntity(rSet);
    }

    public Optional<PageVisitRecordEntity> get(Integer id) {
        Optional<ResultSet> rs = driver.getByID(PageVisitRecordEntity.getTableName(),
                PageVisitRecordEntity.getColumnNames(), PageVisitRecordEntity.getBaseDataFields().get(0),
                id.toString());
        if (rs.isEmpty()) {
            return Optional.ofNullable(null);
        }

        ResultSet rSet = rs.get();

        Optional<List<PageVisitRecordEntity>> optional = this.convertToEntity(rSet);
        if (optional.isEmpty())
            return Optional.ofNullable(null);

        List<PageVisitRecordEntity> entity = optional.get();
        if (entity.size() > 1) {
            Log.printLog(Level.WARNING,
                    String.format("Find by id return more than 1 record, got total record: %d", entity.size()));
            for (PageVisitRecordEntity pageVisitRecordEntity : entity) {
                Log.printLog(Level.DEBUG, "Record #" + pageVisitRecordEntity.toString());
            }
            return Optional.ofNullable(null);
        }

        if (entity.size() == 0) {
            Log.printLog(Level.INFO, String.format("Can't found any record with id = %d in %s table", id,
                    PageVisitRecordEntity.getTableName()));
            return Optional.ofNullable(null);
        }

        return Optional.of(entity.get(0));
    }

    public PageVisitRecordEntity save(PageVisitRecord t) {
        PageVisitRecordEntity entity = new PageVisitRecordEntity(this.currentId, false, t);
        this.currentId += 1;
        String sqlStmt = this.constructInsertStatement(entity);
        driver.insert(sqlStmt);
        return entity;
    }

    public void update(PageVisitRecordEntity entity) {
        String sqlStmt = this.constructUpdateStatement(entity);
        driver.update(sqlStmt);
    };

    public void delete(PageVisitRecordEntity entity) {
        entity.remove();
        try {
            update(entity);
        } catch (Exception e) {
            Log.printLog(Level.WARNING, "Fail to delete element, got Error:" + e.getMessage());
        }
    }

    // MySQL create table sql command
    public static String createTableMySQLCommand() {
        return """
                CREATE TABLE `record` (
                  `ID` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
                  `IS_DELETE` boolean NOT NULL DEFAULT '0',
                  `DATE_CREATED` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                  `DATE_UPDATED` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
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
                  "DATE_CREATED" timestamp NOT NULL,
                  "DATE_UPDATED" timestamp NOT NULL,
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
