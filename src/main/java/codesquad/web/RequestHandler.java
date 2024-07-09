package codesquad.web;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.type.HttpMethod;
import java.io.IOException;

public interface RequestHandler {

    default void process(HttpRequest request, HttpResponse response) throws IOException {
        HttpMethod requestMethod = request.getHttpMethod();

        if (requestMethod == HttpMethod.GET) {
            handleGet(request, response);
        }
        if (requestMethod == HttpMethod.POST) {
            handlePost(request, response);
        }
    }

    default void handleGet(HttpRequest request, HttpResponse response) throws IOException {
    }

    default void handlePost(HttpRequest request, HttpResponse response) throws IOException {
    }

    default void handlePut(HttpRequest request, HttpResponse response) {
    }

    default void handlePatch(HttpRequest request, HttpResponse response) {
    }

    default void handleDelete(HttpRequest request, HttpResponse response) {
    }
}
