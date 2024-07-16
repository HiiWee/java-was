package codesquad.was.database;

import codesquad.was.exception.InternalServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate {

    private final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    private final DataSourceManager dataSourceManager = new DataSourceManager();

    public void insert(final String query, final PreparedStatementSetter psSetter) {
        try (Connection conn = dataSourceManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            psSetter.setValues(ps);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerException();
        }
    }

    public <T> T selectOne(final String query, final PreparedStatementSetter setter,
                           final ResultSetMapper<T> mapper) {
        try (Connection conn = dataSourceManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            setter.setValues(ps);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return mapper.map(resultSet);
            }
            return null;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerException();
        }
    }

    public <T> List<T> selectAll(final String query, final ResultSetMapper<T> setter) {
        try (Connection conn = dataSourceManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();

            List<T> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(setter.map(resultSet));
            }

            return results;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerException();
        }
    }

    public void execute(final String query) {
        try (Connection conn = dataSourceManager.getConnection()) {
            conn.createStatement()
                    .execute(query);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new InternalServerException();
        }
    }
}
