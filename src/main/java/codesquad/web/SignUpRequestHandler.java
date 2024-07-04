package codesquad.web;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.model.User;
import java.io.IOException;

public class SignUpRequestHandler implements RequestHandler {

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");

        User user = new User(userId, nickname, password);

//        response.sendRedirect("/home");
    }
}
