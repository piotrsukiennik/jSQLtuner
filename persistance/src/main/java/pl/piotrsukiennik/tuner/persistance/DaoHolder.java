package pl.piotrsukiennik.tuner.persistance;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public abstract class DaoHolder {

    protected static CommonDao commonDao;

    protected static LogDao logDao;

    protected static QueryDao queryDao;

    protected static SchemaDao schemaDao;

    public static LogDao getLogDao() {
        return logDao;
    }

    public static CommonDao getCommonDao() {
        return commonDao;
    }

    public static QueryDao getQueryDao() {
        return queryDao;
    }

    public static SchemaDao getSchemaDao() {
        return schemaDao;
    }

    protected static void setLogDao( LogDao logDao ) {
        DaoHolder.logDao = logDao;
    }

    protected static void setQueryDao( QueryDao queryDao ) {
        DaoHolder.queryDao = queryDao;
    }

    protected static void setSchemaDao( SchemaDao schemaDao ) {
        DaoHolder.schemaDao = schemaDao;
    }

    protected static void setCommonDao( CommonDao commonDao ) {
        DaoHolder.commonDao = commonDao;
    }

}