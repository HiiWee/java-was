package codesquad.web.handler.dto;

import codesquad.web.domain.vo.CommentWithNickname;
import codesquad.web.domain.vo.PostWithNickname;
import java.util.List;

public record PostsInfo(
        Long id,
        String writerImageName,
        String writerNickname,
        String title,
        String content,
        String postImageName,
        List<CommentInfo> commentInfos
) {
    public static PostsInfo from(final PostWithNickname post, final List<CommentWithNickname> commentWithNicknames) {
        List<CommentInfo> commentInfos = commentWithNicknames.stream()
                .map(comment -> new CommentInfo(comment.nickname(), comment.imageName(), comment.content()))
                .toList();

        return new PostsInfo(post.id(), post.userImageName(), post.nickname(), post.title(), post.content(),
                post.postImageName(), commentInfos);
    }
}
