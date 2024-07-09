package codesquad.web;

import codesquad.model.User;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpRequestHandler implements RequestHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handlePost(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User(userId, nickname, password, email);
        log.info("회원가입 유저 = {}", user);

        response.sendRedirect("/main");
    }
}
