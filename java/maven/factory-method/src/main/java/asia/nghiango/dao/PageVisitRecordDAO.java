package asia.nghiango.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import asia.nghiango.dbhelper.DatabaseHandler;
import asia.nghiango.dbhelper.InMemoryDatabaseHandler;
import asia.nghiango.entities.Entity;
import asia.nghiango.entities.PageVisitRecordEntity;
import asia.nghiango.model.PageVisitRecord;
import asia.nghiango.utilities.Env;

/**
 * PageVisitRecordDAO should have all SQL/Databse related infomation to storing
 * {@link PageVisitRecord} busines object model
 */
public class PageVisitRecordDAO implements DataAccessObject<PageVisitRecord> {
    private DatabaseHandler driver;

    public PageVisitRecordDAO() {
        this.driver = new InMemoryDatabaseHandler();
    }

    public PageVisitRecordDAO(DatabaseHandler driver) {
        this.driver = driver;
    }

    @SuppressWarnings("unchecked")
    public List<PageVisitRecordEntity> getAll() {
        List<String> colNames = new ArrayList<String>();
        colNames.addAll(Entity.getColumnNames());
        colNames.addAll(PageVisitRecord.getColumnNames());
        return (List<PageVisitRecordEntity>) this.driver.getAll(PageVisitRecord.getTableNames(), colNames);
    };

    @SuppressWarnings("unchecked")
    public Optional<PageVisitRecordEntity> get(int id) {
        return (Optional<PageVisitRecordEntity>) this.driver.get(PageVisitRecord.getTableNames(),
                PageVisitRecord.getColumnNames(), id);
    };

    public Entity save(PageVisitRecord t) {
        return this.driver.save(t);
    };

    public void update(Entity t) {
        this.driver.update(t);
    };

    public void delete(Entity t) {
        this.driver.delete(t);
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
                //break;

            case MYSQL:
                this.driver.createTable(createTableMySQLCommand());
                break;

            case POSTGRESQL:
                this.driver.createTable(createTablePostgreSQLCommand());
                break;

            default:
                throw new UnsupportedOperationException("File storage isn't implemented yet");
                //break;
        }
        // this.driver.createTable(tableName, column, extras);
    }
}
