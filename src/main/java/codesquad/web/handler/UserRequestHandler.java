package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.ContextHolder;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.MimeType;
import codesquad.web.io.InMemoryUserDataBase;
import codesquad.web.model.User;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import codesquad.web.snippet.SnippetBuilder;
import codesquad.web.snippet.SnippetFixture;
import java.io.IOException;
import java.util.List;

public class UserRequestHandler extends AbstractRequestHandler {

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String sessionId = ContextHolder.getContext();
        HttpSession session = request.getSession(false);

        if (sessionId == null || session == null) {
            response.forward("/index.html");
            return;
        }

        List<User> signedUpUsers = InMemoryUserDataBase.findAll();
        Snippet loginHeaderSnippet = createLoginHeaderSnippet((User) session.getAttribute(sessionId));
        Snippet userListSnippet = createUserListSnippet(signedUpUsers);

        String completeSnippet = ResourceSnippetBuilder.builder()
                .templatePath("/user/list.html")
                .snippets(List.of(loginHeaderSnippet, userListSnippet))
                .build()
                .getCompleteSnippet();

        response.dynamicForward(completeSnippet.getBytes(), MimeType.HTML);
    }

    private Snippet createLoginHeaderSnippet(final User user) {
        return SnippetBuilder.builder()
                .snippet(SnippetFixture.LOGIN_HEADER)
                .attributes(List.of(user.getNickname()))
                .build();
    }

    private Snippet createUserListSnippet(final List<User> users) {
        List<Snippet> snippets = users.stream()
                .map(user -> SnippetBuilder.builder()
                        .snippet(SnippetFixture.USER_INFO)
                        .attributes(List.of(user.getNickname(), user.getUserId(), user.getEmail()))
                        .build())
                .toList();

        return Snippet.combineAll(snippets);
    }
}
