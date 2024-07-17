package codesquad.web.domain;

public class Post {

    private Long id;
    private final String title;
    private final String content;
    private final Long userPrimaryId;

    public Post(final String title, final String content, final long userPrimaryId) {
        this.title = title;
        this.content = content;
        this.userPrimaryId = userPrimaryId;
    }

    public Post(final Long id, final String title, final String content, final Long userPrimaryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userPrimaryId = userPrimaryId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserPrimaryId() {
        return userPrimaryId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userPrimaryId=" + userPrimaryId +
                '}';
    }
}
