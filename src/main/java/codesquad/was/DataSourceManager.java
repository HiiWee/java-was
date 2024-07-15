package codesquad.was;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceManager {

    private final Logger log = LoggerFactory.getLogger(DataSourceManager.class);

    private static DataSource dataSource;

    public DataSourceManager() {
        Properties props = new Properties();
        loadProperties(props);
        String url = props.getProperty("datasource.url");
        String user = props.getProperty("datasource.user");
        String password = props.getProperty("datasource.password");

        dataSource = JdbcConnectionPool.create(url, user, password);
    }

    private void loadProperties(final Properties props) {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("config/datasource.properties"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
