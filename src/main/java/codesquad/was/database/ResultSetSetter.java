package codesquad.was.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetSetter<T> {

    T setValues(ResultSet resultSet) throws SQLException;
}
