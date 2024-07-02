package codesquad.http.type;

import static org.assertj.core.api.Assertions.assertThat;

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
    void 존재하지_않는_httpHeader_이름이라면_Dummy_Header를_반환한다() {
        // given
        String headerName = "invalidHeaderName";

        // when
        HeaderType headerType = HeaderType.find(headerName);

        // then
        assertThat(headerType).isEqualTo(HeaderType.NONE);
    }
}
