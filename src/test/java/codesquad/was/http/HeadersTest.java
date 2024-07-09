package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.was.http.type.HeaderType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
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

    @Test
    void 추가된_Header들의_출력용_메시지를_만들_수_있다() {
        // given
        Headers headers = new Headers();
        headers.add(HeaderType.CONTENT_TYPE, "text/html");
        headers.add(HeaderType.CONTENT_TYPE, "text/plain");
        headers.addCookies(List.of(new Cookie("key1", "value1"), new Cookie("key2", "value2")));

        // when
        String headerMessage = headers.createMessage();

        // then
        assertThat(headerMessage).isEqualTo(
                "Content-Type: text/html; text/plain\r\nSet-Cookie: key1=value1\r\nSet-Cookie: key2=value2");
    }
}
