package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.ContextHolder;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.MimeType;
import codesquad.web.domain.PostRepository;
import codesquad.web.domain.User;
import codesquad.web.domain.vo.PostWithNickname;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import codesquad.web.snippet.SnippetBuilder;
import codesquad.web.snippet.SnippetFixture;
import java.io.IOException;
import java.util.List;

public class HomeRequestHandler extends AbstractRequestHandler {

    private final PostRepository postRepository;

    public HomeRequestHandler(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String sessionId = ContextHolder.getContext();
        HttpSession session = request.getSession(false);

        Snippet headerSnippet;
        List<PostWithNickname> posts = postRepository.findAllWithJoinUser();
        Snippet postsSnippet = createPostsSnippet(posts);

        if (sessionId == null || session == null) {
            headerSnippet = new Snippet(SnippetFixture.NONE_LOGIN_HEADER);
            String postsResourceSnippet = createPostsResourceSnippet(headerSnippet, postsSnippet);
            response.dynamicForward(postsResourceSnippet.getBytes(), MimeType.HTML);
            return;
        }

        User user = (User) session.getAttribute(sessionId);
        headerSnippet = createLoginHeaderSnippet(user);
        String postsResourceSnippet = createPostsResourceSnippet(headerSnippet, postsSnippet);

        response.dynamicForward(postsResourceSnippet.getBytes(), MimeType.HTML);
    }

    private Snippet createPostsSnippet(final List<PostWithNickname> posts) {
        List<Snippet> snippets = posts.stream()
                .map(post -> SnippetBuilder.builder()
                        .snippet(SnippetFixture.POST_INFO)
                        .attributes(List.of(post.nickname(), post.title(), post.content()))
                        .build())
                .toList();

        return Snippet.combineAll(snippets);
    }

    private Snippet createLoginHeaderSnippet(final User user) {
        return SnippetBuilder.builder()
                .snippet(SnippetFixture.LOGIN_HEADER)
                .attributes(List.of(user.getNickname()))
                .build();
    }

    private String createPostsResourceSnippet(final Snippet loginHeaderSnippet, final Snippet postsSnippet) {
        return ResourceSnippetBuilder.builder()
                .snippets(List.of(loginHeaderSnippet, postsSnippet))
                .templatePath("/index.html")
                .build()
                .getCompleteSnippet();
    }
}
