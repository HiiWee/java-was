package codesquad.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class RequestLineTest {

    @Test
    void httpRequestLine을_받으면_각_메소드를_파싱하여_RequestLine을_생성한다() throws IOException {
        // given
        BufferedReader requestReader = new BufferedReader(new StringReader("GET /index.html HTTP/1.1"));

        // when
        RequestLine requestLine = new RequestLine(requestReader.readLine());

        // then
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
                () -> assertThat(requestLine.getRequestPath()).isEqualTo("/index.html"),
                () -> assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1")
        );
    }
}
