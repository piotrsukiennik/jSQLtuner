package pl.piotrsukiennik.tuner.statement;

import java.sql.Statement;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface StatementBuilder {
    Statement build( Statement statement );
}
