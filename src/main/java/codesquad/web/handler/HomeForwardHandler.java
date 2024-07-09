package codesquad.web.handler;

import codesquad.was.RequestHandler;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import java.io.IOException;

public class HomeForwardHandler implements RequestHandler {

    @Override
    public void process(final HttpRequest request, final HttpResponse response) throws IOException {
        response.forward("/index.html");
    }
}
