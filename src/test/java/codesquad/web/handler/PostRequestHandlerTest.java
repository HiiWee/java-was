package codesquad.web.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.web.domain.Post;
import codesquad.web.handler.fixture.RequestHandlerTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PostRequestHandlerTest extends RequestHandlerTest {

    private String sessionId;

    @AfterEach
    void tearDown() throws IOException {
        로그아웃을_한다(sessionId);
    }

    @Nested
    class 글쓰기_페이지에_GET요청을_할_때 {

        @Nested
        class 만약_로그인이_되어있지_않다면 {

            @Test
            void 응답_코드로_302를_반환한다() throws IOException {
                // given
                PostRequestHandler postRequestHandler = new PostRequestHandler(postRepository);
                String httpRequestValue =
                        "GET /post HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "\r\n";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                postRequestHandler.handleGet(request, response);

                // then
                assertThat(response).extracting(HttpResponse::getStatusCode)
                        .isEqualTo("302");
            }
        }

        @Nested
        class 만약_로그인이_되어있다면 {

            @Test
            void 응답_코드로_200을_반환한다() throws IOException {
                // given
                회원가입을_한다();
                sessionId = 로그인을_한다();
                UserRequestHandler userRequestHandler = new UserRequestHandler(userRepository);
                String httpRequestValue =
                        "GET /post HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "Cookie: sid=" + sessionId + "\r\n"
                                + "\r\n";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                userRequestHandler.handleGet(request, response);

                // then
                assertThat(response).extracting(HttpResponse::getStatusCode)
                        .isEqualTo("200");
            }
        }
    }

    @Nested
    class 글쓰기를_완료하고_POST_요청을_보낼때 {

        @Nested
        class 만약_로그인이_되어있지_않다면 {

            @Test
            void 응답_코드로_302를_반환한다() throws IOException {
                // given
                PostRequestHandler postRequestHandler = new PostRequestHandler(postRepository);
                String httpRequestValue =
                        "POST /post HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "Content-Type: application/x-www-form-urlencoded\r\n"
                                + "Content-Length: " + "title=title&content=content".getBytes("UTF-8").length + "\r\n"
                                + "\r\n"
                                + "title=title&content=content";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                postRequestHandler.handlePost(request, response);

                // then
                assertThat(response).extracting(HttpResponse::getStatusCode)
                        .isEqualTo("302");
            }
        }

        @Nested
        class 만약_로그인이_되어있다면 {

            @Test
            void 게시글을_저장하고_응답_코드로_200을_반환한다() throws IOException {
                // given
                회원가입을_한다();
                sessionId = 로그인을_한다();
                PostRequestHandler postRequestHandler = new PostRequestHandler(postRepository);
                String httpRequestValue =
                        "POST /post HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "Content-Type: application/x-www-form-urlencoded\r\n"
                                + "Content-Length: " + "title=title&content=content".getBytes("UTF-8").length + "\r\n"
                                + "\r\n"
                                + "title=title&content=content";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                postRequestHandler.handlePost(request, response);
                Post post = postRepository.findById(1L).get();

                // then
                assertAll(
                        () -> assertThat(response).extracting(HttpResponse::getStatusCode)
                                .isEqualTo("302"),
                        () -> assertThat(post.getTitle()).isEqualTo("title"),
                        () -> assertThat(post.getContent()).isEqualTo("content")
                );
            }
        }
    }
}
