package io.github.gdpl2112.database.builder;

import io.github.gdpl2112.database.DataBase;
import io.github.gdpl2112.database.Table;

/**
 * @author github.kloping
 */
public class TableBuilder {
    protected Table table = null;
    protected DataBase dataBase;
    protected FieldBuilder builder = null;
    private Integer st = 1;
    private String tableName = null;

    public TableBuilder(String tableName, DataBase dataBase) {
        this.tableName = tableName;
        this.dataBase = dataBase;
        table = new Table();
        table.setName(tableName);
    }

    public FieldBuilder addField(Class cla) {
        builder = new FieldBuilder(cla, this, st++);
        return builder;
    }

    public Table build() {
        dataBase.getTableMap().put(tableName, table);
        dataBase.flush();
        return table;
    }
}
