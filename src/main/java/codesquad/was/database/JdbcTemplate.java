package codesquad.was.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    private final DataSourceManager dataSourceManager = new DataSourceManager();

    public void insert(final String sql, final PreparedStatementSetter psSetter) {
        try (Connection conn = dataSourceManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            psSetter.setValues(ps);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T selectOne(final String sql, final PreparedStatementSetter psSetter,
                           final ResultSetSetter<T> rsSetter) {
        try (Connection conn = dataSourceManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            psSetter.setValues(ps);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return rsSetter.setValues(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> selectAll(final String sql, final ResultSetSetter<T> rsSetter) {
        try (Connection conn = dataSourceManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            List<T> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(rsSetter.setValues(resultSet));
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(final String sql) {
        try (Connection conn = dataSourceManager.getConnection()) {
            conn.createStatement()
                    .execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
