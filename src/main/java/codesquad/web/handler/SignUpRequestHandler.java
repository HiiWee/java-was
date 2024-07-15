package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.exception.BadRequestException;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.web.model.User;
import codesquad.web.repository.UserRepository;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpRequestHandler extends AbstractRequestHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public SignUpRequestHandler(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handlePost(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        validateSignUpInfo(userId, nickname, password, email);

        User user = new User(userId, nickname, password, email);
        log.info("회원가입 유저 = {}", user);

        userRepository.save(user);
        response.sendRedirect("/");
    }

    private void validateSignUpInfo(final String userId, final String nickname, final String password,
                                    final String email) {
        if (Objects.isNull(userId) || Objects.isNull(nickname) || Objects.isNull(password) || Objects.isNull(email)) {
            throw new BadRequestException("회원가입 정보를 모두 입력해야 합니다.");
        }
    }
}
