package codesquad.was.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    private final DataSourceManager dataSourceManager = new DataSourceManager();

    public void insert(final String query, final PreparedStatementSetter psSetter) {
        try (Connection conn = dataSourceManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            psSetter.setValues(ps);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public void execute(final String query) {
        try (Connection conn = dataSourceManager.getConnection()) {
            conn.createStatement()
                    .execute(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}