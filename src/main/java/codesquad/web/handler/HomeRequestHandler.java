package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.http.Cookie;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.MimeType;
import codesquad.web.model.User;
import codesquad.web.snippet.LoginSnippet;
import codesquad.web.snippet.ResourceSnippet;
import codesquad.web.snippet.ResourceSnippetBuilder;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeRequestHandler extends AbstractRequestHandler {

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        List<Cookie> cookies = request.getCookies();
        if (Objects.isNull(cookies) || cookies.isEmpty()) {
            ResourceSnippet resourceSnippet = createNoLoginSnippet();
            System.out.println(resourceSnippet.getCompleteSnippet());
        }

        Cookie loginCookie = cookies.stream()
                .filter(cookie -> cookie.isKey("sid"))
                .findAny()
                .orElse(null);

        HttpSession session = request.getSession(false);

        if (Objects.isNull(loginCookie) || Objects.isNull(session)) {
            ResourceSnippet resourceSnippet = createNoLoginSnippet();
            String completeSnippet = resourceSnippet.getCompleteSnippet();
            response.dynamicForward(completeSnippet.getBytes(), MimeType.HTML);
            return;
        }
        User user = (User) session.getAttribute(loginCookie.getValue());
        ResourceSnippet loginResourceSnippet = createLoginSnippet(user);
        String completeSnippet = loginResourceSnippet.getCompleteSnippet();
        response.dynamicForward(completeSnippet.getBytes(), MimeType.HTML);
    }

    private ResourceSnippet createNoLoginSnippet() throws IOException {
        return ResourceSnippetBuilder.builder()
                .templatePath("/index.html")
                .snippet(LoginSnippet.NO_LOGIN_HEADER)
                .build();
    }

    private ResourceSnippet createLoginSnippet(final User user) throws IOException {
        return ResourceSnippetBuilder.builder()
                .templatePath("/index.html")
                .snippet(LoginSnippet.LOGIN_HEADER)
                .addAttribute(user.getNickname())
                .build();
    }
}
