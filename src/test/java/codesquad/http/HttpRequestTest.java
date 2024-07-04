package codesquad.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @Nested
    class HttpRequest가_들어오면 {

        @Test
        void httpRequest_객체를_생성한다() throws IOException {
            // given
            String httpRequestValue =
                    "POST /submit-form HTTP/1.1\r\n"
                            + "Host: localhost\r\n"
                            + "Connection: keep-alive\r\n"
                            + "Content-Type: application/x-www-form-urlencoded\r\n"
                            + "Content-Length: 13\r\n"
                            + "\r\n"
                            + "name=JohnDoe";
            InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));

            // when
            HttpRequest httpRequest = new HttpRequest(clientInput);

            // then
            assertAll(
                    () -> assertThat(httpRequest.getRequestPath()).isEqualTo("/submit-form"),
                    () -> assertThat(httpRequest.getHttpVersion()).isEqualTo("HTTP/1.1")
            );
        }

        @Test
        void URI에서_쿼리파라미터를_디코딩하고_파싱할_수_있다() throws IOException {
            // given
            String httpRequestValue =
                    "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n"
                            + "Host: localhost\r\n"
                            + "Connection: keep-alive\r\n"
                            + "Content-Type: application/x-www-form-urlencoded\r\n"
                            + "Content-Length: 13\r\n"
                            + "\r\n"
                            + "name=JohnDoe";
            InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));

            // when
            HttpRequest httpRequest = new HttpRequest(clientInput);

            // then
            assertAll(
                    () -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
                    () -> assertThat(httpRequest.getParameter("password")).isEqualTo("password"),
                    () -> assertThat(httpRequest.getParameter("name")).isEqualTo("박재성"),
                    () -> assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net")
            );
        }

        @Test
        void URI에서_쿼리파라미터의_value가_없으면_제외된다() throws IOException {
            // given
            String httpRequestValue =
                    "GET /create?userId=&password=&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n"
                            + "Host: localhost\r\n"
                            + "Connection: keep-alive\r\n"
                            + "Content-Type: application/x-www-form-urlencoded\r\n"
                            + "Content-Length: 13\r\n"
                            + "\r\n"
                            + "name=JohnDoe";
            InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));

            // when
            HttpRequest httpRequest = new HttpRequest(clientInput);

            // then
            assertAll(
                    () -> assertThat(httpRequest.getParameter("userId")).isNull(),
                    () -> assertThat(httpRequest.getParameter("password")).isNull(),
                    () -> assertThat(httpRequest.getParameter("name")).isEqualTo("박재성"),
                    () -> assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net")
            );
        }
    }
}
