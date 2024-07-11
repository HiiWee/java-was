package codesquad.was;

import codesquad.was.exception.CommonException;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.type.HttpMethod;
import codesquad.was.http.type.MimeType;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRequestHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractRequestHandler.class);

    public void process(HttpRequest request, HttpResponse response) throws IOException {
        HttpMethod requestMethod = request.getHttpMethod();

        try {
            if (requestMethod == HttpMethod.GET) {
                handleGet(request, response);
            }
            if (requestMethod == HttpMethod.POST) {
                handlePost(request, response);
            }
        } catch (CommonException e) {
            log.error(e.getMessage(), e);
            String errorPage = ResourceSnippetBuilder.builder()
                    .templatePath("/error/error-template.html")
                    .snippets(List.of(new Snippet(e.getStatusCodeType().getStatusCode()), new Snippet(e.getMessage())))
                    .build()
                    .getCompleteSnippet();

            response.sendError(errorPage.getBytes(), e.getStatusCodeType(), MimeType.HTML);
        }
    }
}
