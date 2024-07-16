package codesquad.web.infrastructure;

import codesquad.was.database.JdbcTemplate;
import codesquad.web.domain.CommentRepository;
import codesquad.web.domain.vo.CommentWithNickname;
import java.util.List;

public class JdbcCommentRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCommentRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS comment (\n"
                        + "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\n"
                        + "    content TEXT,\n"
                        + "    user_primary_id BIGINT,\n"
                        + "    post_primary_id BIGINT,\n"
                        + "    FOREIGN KEY (user_primary_id) REFERENCES users(id),\n"
                        + "    FOREIGN KEY (post_primary_id) REFERENCES post(id)\n"
                        + ")");
    }

    @Override
    public List<CommentWithNickname> findAllByPostIdWithJoinUser(final Long postId) {
        String sql = "SELECT u.user_id, c.content FROM comment c join users u on u.id=c.user_primary_id where c.post_primary_id=?";
        return jdbcTemplate.selectAll(
                sql,
                ps -> ps.setLong(1, postId),
                rs -> new CommentWithNickname(
                        rs.getString("user_id"),
                        rs.getString("content")
                )
        );
    }
}
