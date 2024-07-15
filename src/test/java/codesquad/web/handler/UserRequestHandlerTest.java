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

class UserRequestHandlerTest extends RequestHandlerTest {

    @Nested
    class 사용자_목록을_요청할때 {

        @Nested
        class 만약_로그인이_되어있지_않으면 {

            @Test
            void 응답_코드로_302를_반환한다() throws IOException {
                // given
                UserRequestHandler userRequestHandler = new UserRequestHandler(userRepository);
                String httpRequestValue =
                        "GET /user/list HTTP/1.1\r\n"
                                + "Host: localhost\r\n"
                                + "Connection: keep-alive\r\n"
                                + "\r\n";
                InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
                HttpRequest request = new HttpRequest(clientInput);
                HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

                // when
                userRequestHandler.handleGet(request, response);

                // then
                assertThat(response).extracting(HttpResponse::getStatusCode)
                        .isEqualTo("302");
            }

        }

        @Nested
        class 만약_로그인이_되어있으면 {


            @Test
            void 응답_코드로_200을_반환한다() throws IOException {
                // given
                회원가입을_한다();
                로그인을_한다();
                UserRequestHandler userRequestHandler = new UserRequestHandler(userRepository);
                String httpRequestValue =
                        "GET /user/list HTTP/1.1\r\n"
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
}
