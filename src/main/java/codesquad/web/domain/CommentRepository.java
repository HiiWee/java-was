package codesquad.web.domain;

import codesquad.web.domain.vo.CommentWithNickname;
import java.util.List;

public interface CommentRepository {

    List<CommentWithNickname> findAllByPostIdWithJoinUser(Long postId);
}
