package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.was.http.type.StatusCodeType;
import org.junit.jupiter.api.Test;

class StatusLineTest {

    @Test
    void status_line에_대한_응답_메시지를_생성한다() {
        // given
        StatusLine statusLine = new StatusLine("HTTP/1.1");
        statusLine.setResponseStatus(StatusCodeType.BAD_REQUEST);

        // when
        String message = statusLine.createMessage();

        // then
        assertThat(message).isEqualTo("HTTP/1.1 400 Bad Request");
    }
}
