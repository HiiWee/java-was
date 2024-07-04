package codesquad.web;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.model.User;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpRequestHandler implements RequestHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");

        User user = new User(userId, nickname, password);
        log.info("회원가입 유저 = {}", user);

        response.sendRedirect("/main");
    }
}
