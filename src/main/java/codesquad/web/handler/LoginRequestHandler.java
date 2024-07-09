package codesquad.web.handler;

import codesquad.model.User;
import codesquad.was.AbstractRequestHandler;
import codesquad.was.http.Cookie;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.web.io.InMemoryUserDataBase;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        UUID uuid = UUID.randomUUID();
        session.setAttribute(uuid.toString(), user);

        Cookie cookie = new Cookie("sid", uuid.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        log.info("sign in user = {}", user);
        response.sendRedirect("/main");
    }

    private void validateUserInfo(final String userId, final String password) {
        if (Objects.isNull(userId) || Objects.isNull(password)) {
            throw new IllegalArgumentException("사용자의 정보를 찾을 수 없습니다. userId = " + userId + ", password = " + password);
        }
    }
}
