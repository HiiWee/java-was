package codesquad.web.handler;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.web.RequestHandler;
import java.io.IOException;

public class HomeForwardHandler implements RequestHandler {

    @Override
    public void process(final HttpRequest request, final HttpResponse response) throws IOException {
        response.forward("/index.html");
    }
}
