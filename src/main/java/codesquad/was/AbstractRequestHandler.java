package codesquad.was;

import static codesquad.was.http.type.StatusCodeType.INTERNAL_SERVER_ERROR;

import codesquad.was.exception.CommonException;
import codesquad.was.http.Cookie;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.type.HttpMethod;
import codesquad.was.http.type.MimeType;
import codesquad.was.http.type.StatusCodeType;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRequestHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractRequestHandler.class);

    public void process(HttpRequest request, HttpResponse response) {
        HttpMethod requestMethod = request.getHttpMethod();

        Cookie cookie = findCookie(request, "sid");
        if (Objects.nonNull(cookie)) {
            ContextHolder.setContext(cookie.getValue());
        }

        try {
            if (requestMethod == HttpMethod.GET) {
                handleGet(request, response);
            }
            if (requestMethod == HttpMethod.POST) {
                handlePost(request, response);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            responseInternalServerError(response);
        } catch (CommonException e) {
            responseErrorPage(response, e);
        }
        ContextHolder.clear();
    }

    private Cookie findCookie(final HttpRequest request, final String cookieKey) {
        return request.getCookies()
                .stream()
                .filter(cookie -> cookie.isKey(cookieKey))
                .findAny()
                .orElse(null);
    }

    private void responseInternalServerError(final HttpResponse response) {
        String errorPage = ResourceSnippetBuilder.builder()
                .templatePath("/error/error-template.html")
                .snippets(List.of(new Snippet(INTERNAL_SERVER_ERROR.getStatusCode()),
                        new Snippet("서버에서 요청을 처리할 수 없습니다.")))
                .build()
                .getCompleteSnippet();

        response(response, INTERNAL_SERVER_ERROR, errorPage);
    }

    private void responseErrorPage(final HttpResponse response, final CommonException e) {
        String errorPage = ResourceSnippetBuilder.builder()
                .templatePath("/error/error-template.html")
                .snippets(List.of(new Snippet(e.getStatusCodeType().getStatusCode()), new Snippet(e.getMessage())))
                .build()
                .getCompleteSnippet();

        response(response, e.getStatusCodeType(), errorPage);
    }

    private void response(final HttpResponse response, final StatusCodeType statusCodeType,
                          final String errorPage) {
        try {
            response.sendError(errorPage.getBytes(), statusCodeType, MimeType.HTML);
        } catch (IOException doNothingException) {
            log.error("에러 페이지로 응답할 수 없습니다.");
        }
    }
}
