package codesquad.web.domain.vo;

public record PostWithNickname(
        String nickname,
        String title,
        String content
) {
}
