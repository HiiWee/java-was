package codesquad.web.domain.vo;

public record PostWithNickname(
        Long id,
        String title,
        String content,
        Long userPrimaryId,
        String nickname
) {
}
