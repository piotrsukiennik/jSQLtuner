package pl.piotrsukiennik.tuner.persistance.model.schema;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@javax.persistence.Table(name = "ColumnEntity")
public class Column extends ValueEntity {
    private Table table;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    private Type type;

    @ManyToOne(cascade = CascadeType.ALL)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
