package codesquad.web.handler;

import codesquad.was.AbstractRequestHandler;
import codesquad.was.ContextHolder;
import codesquad.was.exception.BadRequestException;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.web.domain.Comment;
import codesquad.web.domain.CommentRepository;
import codesquad.web.domain.User;
import java.io.IOException;
import java.util.Objects;

public class CommentRequestHandler extends AbstractRequestHandler {

    private final CommentRepository commentRepository;

    public CommentRequestHandler(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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
        String postId = request.getParameter("postId");
        String content = request.getParameter("content");

        validateCommentInfo(postId, content);

        Comment comment = new Comment(content, loginedUser.getId(), Long.parseLong(postId));
        commentRepository.save(comment);

        response.sendRedirect("/");
    }

    private void validateCommentInfo(final String postId, final String content) {
        if (Objects.isNull(postId) || Objects.isNull(content)) {
            throw new BadRequestException("댓글의 내용은 반드시 입력해야 합니다.");
        }
    }
}
