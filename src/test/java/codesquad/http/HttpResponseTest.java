package codesquad.http;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HttpResponseTest {

    @Nested
    class forward_메소드는 {

        @Nested
        class 잘못된_파일경로를_넘겨주면 {

            @Test
            void 예외가_발생한다() throws IOException {
                // given
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                HttpResponse httpResponse = new HttpResponse(new DataOutputStream(byteArrayOutputStream), "HTTP/1.1");

                // expect
                assertThatThrownBy(() -> httpResponse.forward("/invalidPath"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("파일을 찾을 수 없습니다. requestPath = /invalidPath");
            }
        }
    }
}
