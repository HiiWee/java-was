package codesquad.web.io;

import codesquad.web.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByUserId(String userId);

    List<User> findAll();
}
