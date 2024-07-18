package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.was.http.type.MimeType;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class RequestMessageBodyTest {

    @Test
    void RequestMessageBody에서_form_parameter를_읽을_수_있다() throws IOException {
        // given
        byte[] bodyBytes = "name=사람이름".getBytes();
        RequestMessageBody requestMessageBody = new RequestMessageBody(bodyBytes,
                MimeType.APPLICATION_X_WWW_FORM_ENCODED);

        // when
        String name = requestMessageBody.getParameter("name");

        // then
        assertThat(name).isEqualTo("사람이름");
    }
}
