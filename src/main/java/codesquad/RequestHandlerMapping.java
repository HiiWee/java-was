package codesquad;

import codesquad.utils.StringUtils;
import codesquad.web.MainForwardHandler;
import codesquad.web.RegistrationForwardHandler;
import codesquad.web.RequestHandler;
import codesquad.web.SignUpRequestHandler;
import codesquad.web.StaticResourceForwardHandler;
import java.util.Map;

public class RequestHandlerMapping {

    private static final String STATIC_HANDLER_MAPPING = "static";

    private static final Map<String, RequestHandler> handlerMappings = Map.of(
            "static", new StaticResourceForwardHandler(),
            "/registration", new RegistrationForwardHandler(),
            "/signup", new SignUpRequestHandler(),
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
