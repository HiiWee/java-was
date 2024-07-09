package codesquad.model;

public class User {

    private final long id;
    private final String userId;
    private final String nickname;
    private final String password;
    private final String email;

    public User(final long id, final String userId, final String nickname, final String password, final String email) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
