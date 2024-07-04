package codesquad;

import codesquad.utils.StringUtils;
import codesquad.web.RegistrationRequestHandler;
import codesquad.web.RequestHandler;
import codesquad.web.SignUpRequestHandler;
import codesquad.web.StaticResourceRequestHandler;
import java.util.Map;

public class RequestHandlerMapping {

    private static final Map<String, RequestHandler> handlerMappings = Map.of(
            "static", new StaticResourceRequestHandler(),
            "/registration", new RegistrationRequestHandler(),
            "/signup", new SignUpRequestHandler()
    );

    public RequestHandler read(final String requestPath) {
        if (isStaticPath(requestPath)) {
            return handlerMappings.get("static");
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
