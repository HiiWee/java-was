package codesquad.was;

import codesquad.utils.StringUtils;
import codesquad.web.handler.LoginRequestHandler;
import codesquad.web.handler.SignUpRequestHandler;
import java.util.Map;

public class RequestHandlerMapping {

    private static final String STATIC_HANDLER_MAPPING = "static";

    private final Map<String, RequestHandler> handlerMappings = Map.of(
            "static", (request, response) -> response.forward(request.getRequestPath()),
            "/", (request, response) -> response.forward("/index.html"),
            "/main", (request, response) -> response.forward("/main/index.html"),
            "/registration", (request, response) -> response.forward("/registration/index.html"),
            "/user/login-failed", (request, response) -> response.forward("/login/failed.html"),
            "/user/create", new SignUpRequestHandler(),
            "/user/login", new LoginRequestHandler()
    );

    public RequestHandler read(final String requestPath) {
        if (isStaticPath(requestPath)) {
            return handlerMappings.get(STATIC_HANDLER_MAPPING);
        }

        return handlerMappings.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(requestPath))
                .map(Map.Entry::getValue)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("요청을 찾을 수 없습니다. requestPath = " + requestPath));
    }

    private boolean isStaticPath(final String requestPath) {
        return StringUtils.getFilenameExtension(requestPath) != null;
    }
}
