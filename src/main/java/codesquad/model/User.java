package codesquad.model;

public class User {

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
