package codesquad.model;

public class User {

    private final String userId;
    private final String nickname;
    private final String password;

    public User(final String userId, final String nickname, final String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
