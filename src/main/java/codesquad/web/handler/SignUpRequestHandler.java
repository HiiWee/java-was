package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.web.io.InMemoryUserDataBase;
import codesquad.web.model.User;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpRequestHandler extends AbstractRequestHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handlePost(final HttpRequest request, final HttpResponse response) throws IOException {
        long id = InMemoryUserDataBase.generateId();
        String userId = request.getParameter("userId");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User(id, userId, nickname, password, email);
        log.info("회원가입 유저 = {}", user);

        InMemoryUserDataBase.saveUser(user);
        response.sendRedirect("/");
    }
}
