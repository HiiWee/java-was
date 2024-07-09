package codesquad.web.handler;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import codesquad.was.http.Headers;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.RequestLine;
import codesquad.was.http.RequestMessageBody;
import codesquad.was.http.type.HttpMethod;
import codesquad.web.exception.MethodNotAllowedException;
import java.io.DataOutputStream;
import org.junit.jupiter.api.Test;

class SignUpRequestHandlerTest {

    @Test
    void GET으로_회원가입을_시도하면_예외가_발생한다() {
        // given
        SignUpRequestHandler signUpRequestHandler = new SignUpRequestHandler();

        // expect
        assertThatThrownBy(() -> signUpRequestHandler.handleGet(
                new HttpRequest(new RequestLine(HttpMethod.GET, "/path", "HTTP/1.1"), new Headers(),
                        new RequestMessageBody("data")),
                new HttpResponse(new DataOutputStream(System.out), "HTTP/1.1")))
                .isInstanceOf(MethodNotAllowedException.class)
                .hasMessage("Method Not Allowed");
    }
}
