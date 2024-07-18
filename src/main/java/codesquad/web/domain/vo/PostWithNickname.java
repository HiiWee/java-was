package codesquad.web.domain.vo;

public record PostWithNickname(
        Long id,
        String title,
        String content,
        String imageName,
        Long userPrimaryId,
        String nickname
) {
}
