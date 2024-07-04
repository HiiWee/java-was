package codesquad.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RequestLineTest {

    @Test
    void httpRequestLine을_받으면_각_메소드를_파싱하여_RequestLine을_생성한다() throws IOException {
        // given
        BufferedReader requestReader = new BufferedReader(new StringReader("GET /index.html HTTP/1.1\r\n"));

        // when
        RequestLine requestLine = new RequestLine(requestReader.readLine());

        // then
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
                () -> assertThat(requestLine.getRequestPath()).isEqualTo("/index.html"),
                () -> assertThat(requestLine.getQueryParams()).isEmpty(),
                () -> assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1")
        );
    }

    @Test
    void URI에서_쿼리파라미터를_디코딩하고_파싱할_수_있다() throws IOException {
        // given
        BufferedReader requestReader = new BufferedReader(new StringReader(
                "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n"));

        // when
        RequestLine requestLine = new RequestLine(requestReader.readLine());

        // then
        assertThat(requestLine.getQueryParams()).containsExactlyInAnyOrderEntriesOf(
                Map.of("userId", "javajigi",
                        "password", "password",
                        "name", "박재성",
                        "email", "javajigi@slipp.net")
        );
    }

    @Test
    void URI에서_쿼리파라미터의_value가_없으면_제외된다() throws IOException {
        // given
        BufferedReader requestReader = new BufferedReader(new StringReader(
                "GET /create?userId=&password=&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n"));

        // when
        RequestLine requestLine = new RequestLine(requestReader.readLine());

        // then
        assertThat(requestLine.getQueryParams()).containsExactlyInAnyOrderEntriesOf(
                Map.of("name", "박재성",
                        "email", "javajigi@slipp.net")
        );
    }
}
