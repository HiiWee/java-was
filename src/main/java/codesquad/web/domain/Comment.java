package codesquad.web.domain;

public class Comment {

    private Long id;
    private final String content;
    private final Long usersPrimaryId;
    private final Long postPrimaryId;

    public Comment(final Long id, final String content, final Long usersPrimaryId, final Long postPrimaryId) {
        this.id = id;
        this.content = content;
        this.usersPrimaryId = usersPrimaryId;
        this.postPrimaryId = postPrimaryId;
    }

    public Comment(final String content, final Long usersPrimaryId, final Long postPrimaryId) {
        this.content = content;
        this.usersPrimaryId = usersPrimaryId;
        this.postPrimaryId = postPrimaryId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getUsersPrimaryId() {
        return usersPrimaryId;
    }

    public Long getPostPrimaryId() {
        return postPrimaryId;
    }
}
