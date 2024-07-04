package codesquad.web;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import java.io.IOException;

public interface RequestHandler {

    default void process(HttpRequest request, HttpResponse response) throws IOException {
        String httpMethod = request.getHttpMethod();

        if (httpMethod.equals("GET")) {
            handleGet(request, response);
        }
        if (httpMethod.equals("POST")) {
            handlePost(request, response);
        }
    }

    default void handleGet(HttpRequest request, HttpResponse response) throws IOException {
    }

    default void handlePost(HttpRequest request, HttpResponse response) {
    }

    default void handlePut(HttpRequest request, HttpResponse response) {
    }

    default void handlePatch(HttpRequest request, HttpResponse response) {
    }

    default void handleDelete(HttpRequest request, HttpResponse response) {
    }
}
