package codesquad.web.handler;

import codesquad.model.User;
import codesquad.was.AbstractRequestHandler;
import codesquad.was.http.HttpCookie;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.web.io.InMemoryUserDataBase;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1. 사용자의 로그인 요청을 받음
 * 2. 사용자 정보 검증 -> 아이디 비밀번호 맞는지 -> 실패하면 login_failed.html로 이동
 * 3. 로그인 완료됐으므로, 사용자 정보를 세션에 담음 key는 세션아이디, value는 사용자의 PK id를 담음
 * 4. 세션에 대한 key값을 쿠키로 저장하고 응답을 한다.
 */
public class LoginRequestHandler extends AbstractRequestHandler {

    private final Logger log = LoggerFactory.getLogger(LoginRequestHandler.class);

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        response.forward("/login/form.html");
    }

    @Override
    public void handlePost(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        validateUserInfo(userId, password);
        User user = InMemoryUserDataBase.findByUserId(userId)
                .orElse(null);

        if (Objects.isNull(user) || !user.isValidPassword(password)) {
            response.sendRedirect("/user/login-failed");
        }
        HttpSession session = request.getSession();
        // UUID -> Session 에 유저담음, 그리고 Session의 ID값을 반환받으면

        UUID uuid = UUID.randomUUID();
        session.setAttribute(uuid.toString(), user);

        // TODO 쿠키에 저장

        log.info("sign in user = {}", user);
    }

    private void validateUserInfo(final String userId, final String password) {
        if (Objects.isNull(userId) || Objects.isNull(password)) {
            throw new IllegalArgumentException("사용자의 정보를 찾을 수 없습니다. userId = " + userId + ", password = " + password);
        }
    }
}
