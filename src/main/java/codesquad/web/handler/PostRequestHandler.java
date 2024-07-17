package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.ContextHolder;
import codesquad.was.exception.BadRequestException;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.MimeType;
import codesquad.web.domain.Post;
import codesquad.web.domain.PostRepository;
import codesquad.web.domain.User;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import codesquad.web.snippet.SnippetBuilder;
import codesquad.web.snippet.SnippetFixture;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class PostRequestHandler extends AbstractRequestHandler {

    private final PostRepository postRepository;

    public PostRequestHandler(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String sessionId = ContextHolder.getContext();
        HttpSession session = request.getSession(false);

        if (sessionId == null || session == null) {
            response.sendRedirect("/user/login");
            return;
        }
        Snippet loginHeaderSnippet = createLoginHeaderSnippet((User) session.getAttribute(sessionId));

        String completeSnippet = ResourceSnippetBuilder.builder()
                .templatePath("/post/form.html")
                .snippets(List.of(loginHeaderSnippet))
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

    @Override
    public void handlePost(final HttpRequest request, final HttpResponse response) throws IOException {
        String sessionId = ContextHolder.getContext();
        HttpSession session = request.getSession(false);

        if (sessionId == null || session == null) {
            response.sendRedirect("/user/login");
            return;
        }
        User loginedUser = (User) session.getAttribute(sessionId);

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        validatePost(title, content);

        Post post = new Post(title, content, loginedUser.getId());
        postRepository.save(post);

        response.sendRedirect("/");
    }

    private void validatePost(final String title, final String content) {
        if (Objects.isNull(title) || Objects.isNull(content)) {
            throw new BadRequestException("제목과 본문은 반드시 입력해야 합니다.");
        }
    }
}