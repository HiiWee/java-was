package codesquad.web.domain;

public class User {

    private long id;
    private final String userId;
    private final String nickname;
    private final String password;
    private final String email;

    public User(final String userId, final String nickname, final String password, final String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public User(final long id, final String userId, final String nickname, final String password, final String email) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
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

    public boolean isValidPassword(final String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
