package codesquad.was;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RequestHandlerMappingTest {

    @Test
    void 매핑된_핸들러를_찾지_못하면_null을_반환한다() {
        // given
        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping();

        // when
        RequestHandler handler = requestHandlerMapping.read("/invalidPath");

        // then
        assertThat(handler).isNull();
    }
}
