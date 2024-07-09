package codesquad.was;

import codesquad.utils.StringUtils;
import codesquad.web.handler.HomeForwardHandler;
import codesquad.web.handler.MainForwardHandler;
import codesquad.web.handler.RegistrationForwardHandler;
import codesquad.web.handler.SignUpRequestHandler;
import codesquad.web.handler.StaticResourceForwardHandler;
import java.util.Map;

public class RequestHandlerMapping {

    private static final String STATIC_HANDLER_MAPPING = "static";

    private static final Map<String, RequestHandler> handlerMappings = Map.of(
            "static", new StaticResourceForwardHandler(),
            "/", new HomeForwardHandler(),
            "/registration", new RegistrationForwardHandler(),
            "/user/create", new SignUpRequestHandler(),
            "/main", new MainForwardHandler()
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
