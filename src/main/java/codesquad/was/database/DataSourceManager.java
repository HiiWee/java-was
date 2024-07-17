package codesquad.was.database;

import codesquad.was.exception.InternalServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;

public class DataSourceManager {

    private static DataSource dataSource;

    public DataSourceManager() {
        if (dataSource == null) {
            Properties props = new Properties();
            loadProperties(props);
            String url = props.getProperty("datasource.url");
            String user = props.getProperty("datasource.user");
            String password = props.getProperty("datasource.password");

            dataSource = JdbcConnectionPool.create(url, user, password);
        }
    }

    private void loadProperties(final Properties props) {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("config/datasource.properties"));
        } catch (IOException e) {
            throw new InternalServerException();
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
