package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.ContextHolder;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.MimeType;
import codesquad.web.domain.User;
import codesquad.web.snippet.ResourceSnippet;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import codesquad.web.snippet.SnippetBuilder;
import codesquad.web.snippet.SnippetFixture;
import java.io.IOException;
import java.util.List;

public class HomeRequestHandler extends AbstractRequestHandler {

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String sessionId = ContextHolder.getContext();
        HttpSession session = request.getSession(false);

        if (sessionId == null || session == null) {
            response.forward("/index.html");
            return;
        }
        User user = (User) session.getAttribute(sessionId);
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
