package io.github.gdpl2112.database.builder;

import io.github.gdpl2112.database.e0.Field;

/**
 * @author github.kloping
 */
public class FieldBuilder {
    protected TableBuilder builder;
    private Field field;

    public FieldBuilder(Class cla, TableBuilder builder, Integer st) {
        this.builder = builder;
        field = new Field();
        field.setSt(st);
        field.setClassName(cla.getName());
    }


    public FieldBuilder isUnique(Boolean u) {
        field.setUnique(u);
        return this;
    }

    public FieldBuilder isIncrement(Boolean u) {
        field.setIncrement(u);
        return this;
    }

    public TableBuilder setName(String name) {
        if (builder.table.getFieldMap().containsKey(name)) {
            field = null;
            builder.builder = null;
            return builder;
        }
        field.setName(name);
        builder.table.getFieldMap().put(name, field);
        return builder;
    }


}
