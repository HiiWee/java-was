package codesquad.web.handler.dto;

public record CommentInfo(
        String writerNickname,
        String writerImageName,
        String content
) {
}
