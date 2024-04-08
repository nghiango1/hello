package org.dbhelper;

/**
 * DWDFactory, now i actually hate this pattern the moment I have to implement it
 */
public class DWDFactory<T> {

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
    public DataWriterDriver<T> createDWD(DWDType type) {
        DataWriterDriver<T> dwd;
        if (type == DWDType.IN_MEM) {
            dwd = new InMemoryDWD<T>();
        } else {
            throw new UnsupportedOperationException("Unimplemented factory type");
        }
        return dwd;
    }

    public DataWriterDriver<T> createInMemDWD() {
        return new InMemoryDWD<T>();
    }
}
