package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CookieTest {

    @Test
    void 생성한_쿠키의_메시지를_만들_수_있다() {
        // given
        Cookie cookie = new Cookie("key1", "value1");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(6000);

        // when
        String message = cookie.getMessage();

        // then
        assertThat(message).isEqualTo("key1=value1; HttpOnly; Max-Age=6000; Path=/");
    }
}
