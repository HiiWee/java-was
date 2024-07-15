package codesquad.web.repository;

import codesquad.was.JdbcTemplate;
import codesquad.web.model.User;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public JdbcUserRepository() throws IOException {
    }

    @Override
    public void save(final User user) {
    }

    @Override
    public Optional<User> findByUserId(final String userId) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}
