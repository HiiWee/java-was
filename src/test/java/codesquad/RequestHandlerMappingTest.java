package codesquad;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RequestHandlerMappingTest {

    @Test
    void 매핑된_핸들러를_찾지_못하면_예외가_발생한다ㅣ() {
        // given
        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping();

        // expect
        assertThatThrownBy(() -> requestHandlerMapping.read("/invalidPath"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청을 찾을 수 없습니다. requestPath = /invalidPath");
    }

}
