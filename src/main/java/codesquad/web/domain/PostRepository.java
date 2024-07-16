package codesquad.web.domain;

import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    Optional<Post> findById(long id);
}
