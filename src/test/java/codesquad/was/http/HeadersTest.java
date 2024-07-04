package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.was.http.type.HeaderType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class HeadersTest {

    @Test
    void getHeader_헤더목록에_있는_헤더_값을_찾을_수_있다() throws IOException {
        // given
        String httpRequestHeaders = "Host: localhost\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: text/html\r\n" +
                "\r\n";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpRequestHeaders));
        Headers headers = new Headers(bufferedReader);

        // when
        String headerValue = headers.getHeader(HeaderType.HOST);

        // then
        assertThat(headerValue).isEqualTo("localhost");
    }
}
