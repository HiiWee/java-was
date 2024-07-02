package codesquad.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class MessageBodyTest {

    @Test
    void httpRequest의_요청_바디를_읽을_수_있다() throws IOException {
        // given
        String input = "name=사람이름";
        BufferedReader requestReader = new BufferedReader(new StringReader(input));

        // when
        MessageBody messageBody = new MessageBody(requestReader, String.valueOf(input.length()));

        // then
        assertAll(
                () -> assertThat(messageBody.getBodyData()).isEqualTo(new String(input.getBytes("UTF-8"))),
                () -> assertThat(messageBody.getBodyData().getBytes("UTF-8")).isEqualTo(input.getBytes("UTF-8"))
        );
    }

    @Test
    void httpRequest의_요청바디가_비어있다면_바디는_비어있다() throws IOException {
        // given
        String input = " ";
        BufferedReader requestReader = new BufferedReader(new StringReader(input));

        // when
        MessageBody messageBody = new MessageBody(requestReader, "0");

        // then
        assertThat(messageBody.getBodyData()).isEqualTo("");
    }
}
