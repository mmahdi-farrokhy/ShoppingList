package shop;

import java.sql.SQLException;

public class QueryExecutionException extends RuntimeException {
    public QueryExecutionException(SQLException e) {
        super(e);
    }
}
