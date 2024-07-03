package codesquad.http;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.http.type.HeaderType;
import codesquad.http.type.StatusCodeType;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HttpResponseTest {

    @Nested
    class HttpResponse_객체를_생성하면 {

        @Test
        void HttpResponse_메시지를_만들_수_있다() throws UnsupportedEncodingException {
            // given
            Headers headers = new Headers();
            headers.add(HeaderType.CONTENT_TYPE, "text/html");
            HttpResponse httpResponse = new HttpResponse(new StatusLine("HTTP/1.1", StatusCodeType.OK), headers,
                    new ResponseMessageBody("messageBody".getBytes("UTF-8")));

            // when
            String responseMessage = new String(httpResponse.getResponseBytes());

            // then
            assertThat(responseMessage).isEqualTo("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\nmessageBody");
        }
    }
}
