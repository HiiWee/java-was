package codesquad.web.domain.vo;

public record PostWithNickname(
        Long id,
        String title,
        String content,
        String postImageName,
        Long userPrimaryId,
        String nickname,
        String userImageName
) {
}
