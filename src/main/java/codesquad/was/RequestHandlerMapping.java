package codesquad.was;

import codesquad.utils.StringUtils;
import codesquad.was.database.JdbcTemplate;
import codesquad.web.domain.CommentRepository;
import codesquad.web.domain.PostRepository;
import codesquad.web.domain.UserRepository;
import codesquad.web.handler.CommentRequestHandler;
import codesquad.web.handler.HomeRequestHandler;
import codesquad.web.handler.ImageRequestHandler;
import codesquad.web.handler.LoginRequestHandler;
import codesquad.web.handler.LogoutRequestHandler;
import codesquad.web.handler.PostRequestHandler;
import codesquad.web.handler.SignUpRequestHandler;
import codesquad.web.handler.UserRequestHandler;
import codesquad.web.infrastructure.JdbcCommentRepository;
import codesquad.web.infrastructure.JdbcPostRepository;
import codesquad.web.infrastructure.JdbcUserRepository;
import java.util.HashMap;
import java.util.Map;

public class RequestHandlerMapping {

    private static final String STATIC_HANDLER_MAPPING = "static";

    private final Map<String, RequestHandler> handlerMappings;

    public RequestHandlerMapping() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        UserRepository userRepository = new JdbcUserRepository(jdbcTemplate);
        PostRepository postRepository = new JdbcPostRepository(jdbcTemplate);
        CommentRepository commentRepository = new JdbcCommentRepository(jdbcTemplate);

        handlerMappings = new HashMap<>();
        handlerMappings.put("static", (request, response) -> response.forward(request.getRequestPath()));
        handlerMappings.put("/", new HomeRequestHandler(postRepository, commentRepository));
        handlerMappings.put("/registration", (request, response) -> response.forward("/registration/index.html"));
        handlerMappings.put("/user/login-failed", (request, response) -> response.forward("/login/failed.html"));
        handlerMappings.put("/user/create", new SignUpRequestHandler(userRepository));
        handlerMappings.put("/user/login", new LoginRequestHandler(userRepository));
        handlerMappings.put("/user/logout", new LogoutRequestHandler());
        handlerMappings.put("/user/list", new UserRequestHandler(userRepository));
        handlerMappings.put("/post", new PostRequestHandler(postRepository));
        handlerMappings.put("/post/comment", new CommentRequestHandler(commentRepository));
        handlerMappings.put("/image", new ImageRequestHandler());
    }

    public RequestHandler read(final String requestPath) {
        if (isStaticPath(requestPath)) {
            return handlerMappings.get(STATIC_HANDLER_MAPPING);
        }

        return handlerMappings.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(requestPath))
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(null);
    }

    private boolean isStaticPath(final String requestPath) {
        return StringUtils.getFilenameExtension(requestPath) != null;
    }
}
