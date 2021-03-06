package pl.piotrsukiennik.tuner.model.query.impl;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.piotrsukiennik.tuner.cache.QueryInvalidatonVisitor;
import pl.piotrsukiennik.tuner.model.query.CreateQuery;
import pl.piotrsukiennik.tuner.model.schema.Table;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CreateViewQuery extends CreateQuery {

    private Table view;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public Table getView() {
        return view;
    }

    public void setView( Table view ) {
        this.view = view;
    }

    @Override
    public <R> R accept( QueryInvalidatonVisitor<R> invalidator ) {
        return invalidator.visit( this );
    }
}
