package pl.piotrsukiennik.tuner.model.other;

import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.expression.Expression;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OrderByFragment extends ValueEntity implements SortableByPosition {
    public enum Order {ASC, DESC}

    private Expression orderByExpression;

    private Order orderDirection;

    private int position;


    @Enumerated(value = EnumType.STRING)
    public Order getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection( Order orderDirection ) {
        this.orderDirection = orderDirection;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Expression getOrderByExpression() {
        return orderByExpression;
    }

    public void setOrderByExpression( Expression orderByExpression ) {
        this.orderByExpression = orderByExpression;
    }


    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition( int position ) {
        this.position = position;
    }
}
