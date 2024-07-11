package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.http.Cookie;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.MimeType;
import codesquad.web.model.User;
import codesquad.web.snippet.ResourceSnippet;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import codesquad.web.snippet.SnippetBuilder;
import codesquad.web.snippet.SnippetFixture;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeRequestHandler extends AbstractRequestHandler {

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        List<Cookie> cookies = request.getCookies();

        Cookie loginCookie = cookies.stream()
                .filter(cookie -> cookie.isKey("sid"))
                .findAny()
                .orElse(null);
        HttpSession session = request.getSession(false);

        if (Objects.isNull(loginCookie) || Objects.isNull(session)) {
            response.forward("/index.html");
            return;
        }
        User user = (User) session.getAttribute(loginCookie.getValue());
        ResourceSnippet loginResourceSnippet = createLoginHeaderSnippet(user);
        String completeSnippet = loginResourceSnippet.getCompleteSnippet();
        response.dynamicForward(completeSnippet.getBytes(), MimeType.HTML);
    }

    private ResourceSnippet createLoginHeaderSnippet(final User user) throws IOException {
        Snippet loginSnippet = SnippetBuilder.builder()
                .snippet(SnippetFixture.LOGIN_HEADER)
                .attributes(List.of(user.getNickname()))
                .build();

        return ResourceSnippetBuilder.builder()
                .templatePath("/index.html")
                .snippets(List.of(loginSnippet))
                .build();
    }
}
