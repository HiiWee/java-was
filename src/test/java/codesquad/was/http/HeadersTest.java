package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import codesquad.was.http.type.HeaderType;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class HeadersTest {

    @Test
    void getHeader_헤더목록에_있는_헤더_값을_찾을_수_있다() throws IOException {
        // given
        Headers headers = new Headers(List.of("Host: localhost", "Connection: keep-alive", "Accept: text/html"));

        // when
        String headerValue = headers.getHeader(HeaderType.HOST).get(0);

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

    @Test
    void 헤더_OWS_여부에_관계없이_파싱_할_수_있다() throws IOException {
        // given
        Headers headers = new Headers(
                List.of("Host:localhost", "Connection :keep-alive", "Accept: text/html", "Content-Type :text/html"));

        // when
        String value1 = headers.getHeader(HeaderType.HOST).get(0);
        String value2 = headers.getHeader(HeaderType.CONNECTION).get(0);
        String value3 = headers.getHeader(HeaderType.ACCEPT).get(0);
        String value4 = headers.getHeader(HeaderType.CONTENT_TYPE).get(0);

        // then
        assertAll(
                () -> assertThat(value1).isEqualTo("localhost"),
                () -> assertThat(value2).isEqualTo("keep-alive"),
                () -> assertThat(value3).isEqualTo("text/html"),
                () -> assertThat(value4).isEqualTo("text/html")
        );
    }
}
