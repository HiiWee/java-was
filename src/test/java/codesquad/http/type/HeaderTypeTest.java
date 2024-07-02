package codesquad.http.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class HeaderTypeTest {

    @Test
    void httpHeader_이름과_일치하는_HeaderType을_찾을_수_있다() {
        // given
        String headerName = "content-type";

        // when
        HeaderType headerType = HeaderType.find(headerName);

        // then
        assertThat(headerType).isEqualTo(HeaderType.CONTENT_TYPE);
    }

    @Test
    void 존재하지_않는_httpHeader_이름이라면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> HeaderType.find("invalidHeaderName"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("invalidHeaderName 헤더를 찾을 수 없습니다.");
    }
}
