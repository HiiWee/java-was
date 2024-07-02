package codesquad.http.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
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
    void 일치하는_확장자의_mimeType이_없으면_예외가_발생한다() {
        // expect
        Assertions.assertThatThrownBy(() -> MimeType.findMimeValue("none_extension"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("none_extension에 해당하는 MIME 타입을 찾을 수 없습니다.");
    }
}
