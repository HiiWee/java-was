package codesquad.was;

import codesquad.utils.StringUtils;
import codesquad.web.domain.UserRepository;
import codesquad.web.handler.HomeRequestHandler;
import codesquad.web.handler.LoginRequestHandler;
import codesquad.web.handler.LogoutRequestHandler;
import codesquad.web.handler.SignUpRequestHandler;
import codesquad.web.handler.UserRequestHandler;
import codesquad.web.infrastructure.JdbcUserRepository;
import java.util.Map;

public class RequestHandlerMapping {

    private static final String STATIC_HANDLER_MAPPING = "static";

    private final Map<String, RequestHandler> handlerMappings;

    public RequestHandlerMapping() {
        UserRepository userRepository = new JdbcUserRepository();

        handlerMappings = Map.of(
                "static", (request, response) -> response.forward(request.getRequestPath()),
                "/", new HomeRequestHandler(),
                "/main", (request, response) -> response.forward("/main/index.html"),
                "/registration", (request, response) -> response.forward("/registration/index.html"),
                "/user/login-failed", (request, response) -> response.forward("/login/failed.html"),
                "/user/create", new SignUpRequestHandler(userRepository),
                "/user/login", new LoginRequestHandler(userRepository),
                "/user/logout", new LogoutRequestHandler(),
                "/user/list", new UserRequestHandler(userRepository));
    }

    public RequestHandler read(final String requestPath) {
        if (isStaticPath(requestPath)) {
            return handlerMappings.get(STATIC_HANDLER_MAPPING);
        }

        return handlerMappings.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(requestPath))
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(null);
    }

    private boolean isStaticPath(final String requestPath) {
        return StringUtils.getFilenameExtension(requestPath) != null;
    }
}
