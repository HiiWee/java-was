package codesquad.web.domain.vo;

public record CommentWithNickname(
        String nickname,
        String imageName,
        String content
) {
}
