package pl.piotrsukiennik.tuner.query;

import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.model.schema.*;
import pl.piotrsukiennik.tuner.persistance.service.ISchemaService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:26
 */
public class QueryContextManager {
    private static final String STRIP_TOKENS = "[` !@#$%^&*();'?><_+-=]";
    private Map<String,TableSource> tableMap = new HashMap<String, TableSource>();
    protected QueryContext queryContext;
    protected ISchemaService schemaService;
    protected TableSource lastAttachedTableSource;



    public QueryContextManager(ISchemaService schemaService) {
       this(schemaService,new QueryContext());
    }
    public QueryContextManager(ISchemaService schemaService, QueryContext queryContext) {
        this.queryContext=queryContext;
        this.schemaService=schemaService;
    }

    protected String correctName(String input){
        return input.replaceAll(STRIP_TOKENS,"").toLowerCase();
    }

    public Database getDatabase(String databaseName) {
        String nameCorrected = correctName(databaseName);

        if (queryContext.getDatabase()!=null){
            return queryContext.getDatabase();
        } else {
            Database database = schemaService.getDatabase(nameCorrected);
            queryContext.setDatabase(database);
            return database;
        }
    }
    public Schema getSchema(String schemaName) {
        String schemaNameCorrected = correctName(schemaName);

        if (queryContext.getSchema()!=null){
            return queryContext.getSchema();
        } else {
            Schema schema = schemaService.getSchema(queryContext.getDatabase(),schemaNameCorrected);
            queryContext.setSchema(schema);
            return schema;
        }
    }

    public Column getColumn(Table table, String columnName) {
        String columnNameCorrected = correctName(columnName);
        String key = table.getValue()+" "+columnName;
        Column column = queryContext.getColumns().get(key);
        if (column==null){
            column = schemaService.getColumn(table,columnNameCorrected);
            queryContext.getColumns().put(key,column);
        }
        return column;
    }

    public Column getColumn(String tableName, String columnName) {
        String tableNameCorrected = correctName(tableName);

        String columnNameCorrected = correctName(columnName);

        String key = tableName+" "+columnName;
        Column column = queryContext.getColumns().get(key);
        if (column==null){
            Table table = getTable(tableNameCorrected);
            column = schemaService.getColumn(table,columnNameCorrected);
            queryContext.getColumns().put(key,column);
        }
        return column;
    }
    public Table getTable(String tableName) {
        String nameCorrected = correctName(tableName);

        String key = tableName;
        Table table = queryContext.getTables().get(key);
        if (table==null){
            table = schemaService.getTable(queryContext.getSchema(),nameCorrected);
            queryContext.getTables().put(key,table);
        }
        return table;

    }


    public void putTableSource(TableSource tableSource) {
        if (tableSource.getAlias()==null || tableSource.getAlias().isEmpty()){
            tableMap.put(tableSource.getTable().getValue(),tableSource);
        } else {
            tableMap.put(tableSource.getAlias(),tableSource);
        }
        lastAttachedTableSource=tableSource;

    }
    public TableSource getTableSource(String table){
        return tableMap.get(table);
    }

    public TableSource getLastAttachedTableSource() {
        return lastAttachedTableSource;
    }
}
