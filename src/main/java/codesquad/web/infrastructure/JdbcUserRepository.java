package codesquad.web.infrastructure;

import codesquad.was.database.JdbcTemplate;
import codesquad.web.domain.User;
import codesquad.web.domain.UserRepository;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS users (\n"
                        + "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n"
                        + "    user_id VARCHAR(255),\n"
                        + "    nickname VARCHAR(255),\n"
                        + "    password VARCHAR(255),\n"
                        + "    image_name VARCHAR(255),\n"
                        + "    email VARCHAR(255)\n"
                        + ")");
    }

    @Override
    public void save(final User user) {
        String sql = "INSERT INTO users (user_id, nickname, password, email, image_name) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.insert(sql, ps -> {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getNickname());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getImageName());
        });
    }

    @Override
    public Optional<User> findByUserId(final String userId) {
        String sql = "SELECT id, user_id, nickname, password, email, image_name FROM users where user_id = ?";
        User findUser = jdbcTemplate.selectOne(
                sql,
                ps -> ps.setString(1, userId),
                rs -> new User(
                        rs.getLong("id"),
                        rs.getString("user_id"),
                        rs.getString("nickname"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("image_name")
                )
        );

        return Optional.ofNullable(findUser);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, user_id, nickname, password, email, image_name FROM users";
        return jdbcTemplate.selectAll(
                sql,
                rs -> new User(
                        rs.getLong("id"),
                        rs.getString("user_id"),
                        rs.getString("nickname"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("image_name")
                )
        );
    }
}
