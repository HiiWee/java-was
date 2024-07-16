package codesquad.web.infrastructure;

import codesquad.was.database.JdbcTemplate;
import codesquad.web.domain.Post;
import codesquad.web.domain.PostRepository;
import java.util.Optional;

public class JdbcPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPostRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS post (\n"
                        + "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n"
                        + "    title VARCHAR(255),\n"
                        + "    content TEXT,\n"
                        + "    user_primary_id BIGINT,\n"
                        + "    FOREIGN KEY (user_primary_id) REFERENCES users(id)\n"
                        + ");"
        );
    }

    @Override
    public void save(final Post post) {
        String sql = "INSERT INTO post (title, content, user_primary_id) VALUES (?, ?, ?)";
        jdbcTemplate.insert(
                sql,
                ps -> {
                    ps.setString(1, post.getTitle());
                    ps.setString(2, post.getContent());
                    ps.setLong(3, post.getUserPrimaryId());
                }
        );
    }

    @Override
    public Optional<Post> findById(final long id) {
        String sql = "SELECT id, title, content, user_primary_id FROM post WHERE id = ?";
        Post post = jdbcTemplate.selectOne(
                sql,
                ps -> ps.setLong(1, id),
                rs -> new Post(
                        rs.getLong(1),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getLong("user_primary_id")
                )
        );

        return Optional.ofNullable(post);
    }
}
