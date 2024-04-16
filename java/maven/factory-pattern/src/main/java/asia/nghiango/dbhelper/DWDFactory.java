package asia.nghiango.dbhelper;

/**
 * DWDFactory, now i actually hate this pattern the moment I have to implement it
 */
public class DWDFactory {

    public static enum DWDType {
        IN_MEM,
        FILE,
        MYSQL,
        ORACLE,
        PSQL
    }

    /**
     * A general use factory to create Data Writer Driver with multiple database
     * driver type
     *
     * @param type a enum instant that supported by DWD Factory
     * @return the coresponding DataWriterDriver object base on inputed type
     * @throws throw new UnsupportedOperationException("Unimplemented factory
     *               type");
     */
    public DataWriterDriver createDWD(DWDType type) {
        DataWriterDriver dwd;
        if (type == DWDType.IN_MEM) {
            dwd = new InMemoryDWD();
        } else {
            throw new UnsupportedOperationException("Unimplemented factory type");
        }
        return dwd;
    }

    public DataWriterDriver createInMemDWD() {
        return new InMemoryDWD();
    }
}
