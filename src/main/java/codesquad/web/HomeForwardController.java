package codesquad.web;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import java.io.IOException;

public class HomeForwardController implements RequestHandler {

    @Override
    public void process(final HttpRequest request, final HttpResponse response) throws IOException {
        response.forward("/index.html");
    }
}
