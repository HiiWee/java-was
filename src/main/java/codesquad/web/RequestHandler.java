package codesquad.web;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import java.io.IOException;

public interface RequestHandler {

    void process(HttpRequest request, HttpResponse response) throws IOException;

    default void handleGet(HttpRequest request, HttpResponse response) {
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
