package codesquad.was;

import codesquad.was.exception.CommonException;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.type.HttpMethod;
import java.io.IOException;

public abstract class AbstractRequestHandler implements RequestHandler {

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
            response.sendError(e.getStatusCodeType());
        }
    }
}
