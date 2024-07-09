package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.http.Cookie;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LogoutRequestHandler extends AbstractRequestHandler {

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        List<Cookie> cookies = request.getCookies();

        if (Objects.isNull(cookies) || Objects.isNull(session)) {
            response.sendRedirect("/");
            return;
        }

        Cookie loginCookie = cookies.stream()
                .filter(cookie -> cookie.isKey("sid"))
                .findAny()
                .orElse(null);

        if (Objects.isNull(loginCookie)) {
            response.sendRedirect("/");
            return;
        }

        session.removeAttributes(loginCookie.getValue());
        loginCookie.setMaxAge(0);
        loginCookie.setHttpOnly(true);
        loginCookie.setPath("/");

        response.addCookie(loginCookie);
        response.sendRedirect("/");
    }
}
