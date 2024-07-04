package codesquad;

import codesquad.utils.StringUtils;
import codesquad.web.RequestHandler;
import codesquad.web.StaticResourceRequestHandler;
import java.util.Map;

public class RequestHandlerMapping {

    private static final Map<String, RequestHandler> handlerMappings = Map.of(
            "static", new StaticResourceRequestHandler()
    );

    public RequestHandler read(final String requestPath) {
        if (isStaticPath(requestPath)) {
            return handlerMappings.get("static");
        }
        return null;
    }

    private boolean isStaticPath(final String requestPath) {
        return StringUtils.getFilenameExtension(requestPath) != null;
    }
}
