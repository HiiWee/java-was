package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import codesquad.was.http.type.StatusCodeType;
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
            void 예외가_발생한다() {
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

    @Nested
    class sendRedirect_메소드는 {

        @Nested
        class 리다이렉트할_path를_넘겨주면 {

            @Test
            void 해당_path로_리다이렉트가_지정된_응답_메시지를_생성한다() throws IOException {
                // given
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                HttpResponse httpResponse = new HttpResponse(new DataOutputStream(byteArrayOutputStream), "HTTP/1.1");

                // when
                httpResponse.sendRedirect("/test/path");
                String responseMessage = byteArrayOutputStream.toString("UTF-8");

                // then
                assertThat(responseMessage).isEqualTo("HTTP/1.1 302 Found\r\nLocation: /test/path\r\n\r\n");
            }
        }
    }

    @Nested
    class sendError_메소드는 {

        @Nested
        class 클라이언트로_전송할_에러_상태코드를_넘겨주면 {

            @Test
            void 해당_응답값으로_에러_상태코드를_가진_응답_메시지를_생성한다() throws IOException {
                // given
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                HttpResponse httpResponse = new HttpResponse(new DataOutputStream(byteArrayOutputStream), "HTTP/1.1");

                // when
                httpResponse.sendError(StatusCodeType.BAD_REQUEST);
                String responseMessage = byteArrayOutputStream.toString("UTF-8");

                // then
                assertThat(responseMessage).isEqualTo("HTTP/1.1 400 Bad Request\r\n\r\n");
            }
        }
    }
}
