package codesquad.was;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTemplate {

    private final DataSourceManager dataSourceManager = new DataSourceManager();

    public JdbcTemplate() {
        try {
            Connection connection = dataSourceManager.getConnection();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
