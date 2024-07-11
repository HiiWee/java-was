package codesquad.web.handler;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HomeRequestHandlerTest {

    @Nested
    class 홈_화면을_요청할때 {

        @Nested
        class 만약_로그인이_안되어_있다면 {

            @Test
            void 응답_코드로_200을_반환한다() throws IOException {
                // given
                HomeRequestHandler homeRequestHandler = new HomeRequestHandler();
                String httpRequestValue =
                        "GET / HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "\r\n";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                homeRequestHandler.handleGet(request, response);

                // then
                assertThat(response).extracting(HttpResponse::getStatusCode)
                        .isEqualTo("200");
            }
        }

        @Nested
        class 만약_로그인이_되어있다면 {

            @Test
            void 응답_코드로_200을_반환한다() throws IOException {
                // given
                HandlerFixture.회원가입을_한다();
                String sessionId = HandlerFixture.로그인을_한다();
                HomeRequestHandler homeRequestHandler = new HomeRequestHandler();
                String httpRequestValue =
                        "GET / HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "Cookie: sid=" + sessionId + "\r\n"
                                + "\r\n";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                homeRequestHandler.handleGet(request, response);

                // then
                assertThat(response).extracting(HttpResponse::getStatusCode)
                        .isEqualTo("200");
            }
        }
    }
}
