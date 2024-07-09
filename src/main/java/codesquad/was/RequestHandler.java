package codesquad.was;

import codesquad.was.exception.MethodNotAllowedException;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import java.io.IOException;

@FunctionalInterface
public interface RequestHandler {

    void process(HttpRequest request, HttpResponse response) throws IOException;

    default void handleGet(HttpRequest request, HttpResponse response) throws IOException {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    default void handlePost(HttpRequest request, HttpResponse response) throws IOException {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    default void handlePut(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    default void handlePatch(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    default void handleDelete(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException("Method Not Allowed");
    }
}
