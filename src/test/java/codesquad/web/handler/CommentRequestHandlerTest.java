package codesquad.web.handler;

import static codesquad.was.http.type.StatusCodeType.FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import codesquad.was.ContextHolder;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.web.domain.vo.CommentWithNickname;
import codesquad.web.handler.fixture.RequestHandlerTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CommentRequestHandlerTest extends RequestHandlerTest {

    private String sessionId;

    @AfterEach
    void tearDown() throws IOException {
        로그아웃을_한다(sessionId);
    }

    @Nested
    class 기존_게시글에_댓글_등록을_요청할때 {

        @Nested
        class 만약_로그인이_되어있다면 {

            @Test
            void 해당_게시글에_댓글을_작성하고_응답_코드로_302를_반환한다() throws IOException {
                // given
                회원가입을_한다();
                sessionId = 로그인을_한다();
                게시글을_작성한다();
                CommentRequestHandler commentRequestHandler = new CommentRequestHandler(commentRepository);
                String httpRequestValue =
                        "POST /post/comment HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "Content-Type: application/x-www-form-urlencoded\r\n"
                                + "Content-Length: "
                                + "postId=1&content=thisiscomment".getBytes().length
                                + "\r\n"
                                + "\r\n"
                                + "postId=1&content=thisiscomment";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                commentRequestHandler.handlePost(request, response);
                CommentWithNickname commentWithNickname = commentRepository.findAllByPostIdWithJoinUser(1L).get(0);

                // then
                assertAll(
                        () -> assertThat(response.getStatusCode()).isEqualTo(FOUND.getStatusCode()),
                        () -> assertThat(commentWithNickname.nickname()).isEqualTo("test"),
                        () -> assertThat(commentWithNickname.content()).isEqualTo("thisiscomment")
                );
            }
        }

        @Nested
        class 만약_로그인이_되어있지_않다면 {

            @Test
            void 응답_코드로_302를_반환하고_댓글은_존재하지_않는다() throws IOException {
                // given
                회원가입을_한다();
                sessionId = 로그인을_한다();
                게시글을_작성한다();
                로그아웃을_한다(sessionId);
                ContextHolder.clear();
                CommentRequestHandler commentRequestHandler = new CommentRequestHandler(commentRepository);
                String httpRequestValue =
                        "POST /post/comment HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "Content-Type: application/x-www-form-urlencoded\r\n"
                                + "Content-Length: "
                                + "postId=1&content=thisiscomment".getBytes().length
                                + "\r\n"
                                + "\r\n"
                                + "postId=1&content=thisiscomment";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                commentRequestHandler.handlePost(request, response);
                List<CommentWithNickname> comments = commentRepository.findAllByPostIdWithJoinUser(1L);

                assertAll(
                        () -> assertThat(response.getStatusCode()).isEqualTo(FOUND.getStatusCode()),
                        () -> assertThat(comments).isEmpty()
                );
            }
        }
    }

}
