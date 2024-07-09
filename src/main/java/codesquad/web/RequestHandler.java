package codesquad.web;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.type.HttpMethod;
import codesquad.web.exception.CommonException;
import codesquad.web.exception.MethodNotAllowedException;
import java.io.IOException;

public interface RequestHandler {

    default void process(HttpRequest request, HttpResponse response) throws IOException {
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
