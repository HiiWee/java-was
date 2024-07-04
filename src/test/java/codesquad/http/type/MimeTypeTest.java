package codesquad.http.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MimeTypeTest {

    @Test
    void 확장자의_mimeTypeValue를_찾을_수_있다() {
        // given
        String extension = "html";

        // when
        String mimeValue = MimeType.findMimeValue(extension);

        // then
        assertThat(mimeValue).isEqualTo("text/html");
    }

    @Test
    void 알_수_없는_mimeType이라면_octet_stream이_반환된다() {
        // expect
        assertThat(MimeType.findMimeValue("undefined-type")).isEqualTo("application/octet-stream");
    }
}
