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
                    "POST /submit-form HTTP/1.1\r\n" +
                            "Host: localhost\r\n" +
                            "Connection: keep-alive\r\n" +
                            "Content-Type: application/x-www-form-urlencoded\r\n" +
                            "Content-Length: 13\r\n" +
                            "\r\n" +
                            "name=JohnDoe";
            InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));

            // when
            HttpRequest httpRequest = new HttpRequest(clientInput);

            // then
            assertAll(
                    () -> assertThat(httpRequest.getRequestPath()).isEqualTo("/submit-form"),
                    () -> assertThat(httpRequest.getHttpVersion()).isEqualTo("HTTP/1.1")
            );
        }
    }
}