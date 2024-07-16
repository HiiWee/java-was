package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.ContextHolder;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.MimeType;
import codesquad.web.domain.CommentRepository;
import codesquad.web.domain.PostRepository;
import codesquad.web.domain.User;
import codesquad.web.domain.vo.PostWithNickname;
import codesquad.web.handler.dto.PostsInfo;
import codesquad.web.snippet.ResourceSnippetBuilder;
import codesquad.web.snippet.Snippet;
import codesquad.web.snippet.SnippetBuilder;
import codesquad.web.snippet.SnippetFixture;
import java.io.IOException;
import java.util.List;

public class HomeRequestHandler extends AbstractRequestHandler {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public HomeRequestHandler(final PostRepository postRepository, final CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String sessionId = ContextHolder.getContext();
        HttpSession session = request.getSession(false);

        Snippet headerSnippet;
        List<PostWithNickname> posts = postRepository.findAllWithJoinUser();

        List<PostsInfo> postsInfo = posts.stream()
                .map(post -> PostsInfo.from(post, commentRepository.findAllByPostIdWithJoinUser(post.id())))
                .toList();

        Snippet postsSnippet = createPostsSnippet(postsInfo);

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

    private Snippet createPostsSnippet(final List<PostsInfo> posts) {
        List<Snippet> snippets = posts.stream()
                .map(this::createSinglePostSnippet)
                .toList();

        return Snippet.combineAll(snippets);
    }

    private Snippet createSinglePostSnippet(final PostsInfo post) {
        Snippet postSnippet = SnippetBuilder.builder()
                .snippet(SnippetFixture.POST_INFO)
                .attributes(List.of(post.writerNickname(), post.title(), post.content()))
                .build();

        List<Snippet> postPerCommentSnippets = post.commentInfos()
                .stream()
                .map(comment -> SnippetBuilder.builder()
                        .attributes(List.of(comment.commentWriterNickname(), comment.content()))
                        .snippet(SnippetFixture.COMMENT_INFO)
                        .build())
                .toList();
        Snippet allCommentBySinglePost = Snippet.combineAll(postPerCommentSnippets);

        Snippet complemteCommentSnippet = SnippetBuilder.builder()
                .snippet(SnippetFixture.COMMENT_WRAPPER)
                .attributes(List.of(allCommentBySinglePost.getCompleteSnippet(), String.valueOf(post.id())))
                .build();

        return Snippet.combineAll(List.of(postSnippet, complemteCommentSnippet));
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
