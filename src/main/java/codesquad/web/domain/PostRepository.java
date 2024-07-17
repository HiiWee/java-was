package codesquad.web.domain;

import codesquad.web.domain.vo.PostWithNickname;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    Optional<Post> findById(long id);

    List<PostWithNickname> findAllWithJoinUser();
}
