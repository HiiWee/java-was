package codesquad.web;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import java.io.IOException;

public class StaticResourceRequestHandler implements RequestHandler {

    @Override
    public void process(final HttpRequest request, final HttpResponse response) throws IOException {
        response.forward(request.getRequestPath());
    }
}
