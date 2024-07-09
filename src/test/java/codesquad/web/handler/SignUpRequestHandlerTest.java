package codesquad.web.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import codesquad.model.User;
import codesquad.was.exception.MethodNotAllowedException;
import codesquad.was.http.Headers;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.RequestLine;
import codesquad.was.http.RequestMessageBody;
import codesquad.was.http.type.HttpMethod;
import codesquad.web.io.InMemoryDataBase;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                new HttpResponse(new DataOutputStream(OutputStream.nullOutputStream()), "HTTP/1.1")))
                .isInstanceOf(MethodNotAllowedException.class)
                .hasMessage("Method Not Allowed");
    }

    @Test
    void 회원가입을_하면_메모리_DB에_사용자가_저장된다() throws IOException {
        // given
        SignUpRequestHandler signUpRequestHandler = new SignUpRequestHandler();
        String httpRequestValue =
                "GET /create HTTP/1.1\r\n"
                        + "Host: localhost\r\n"
                        + "Connection: keep-alive\r\n"
                        + "Content-Type: application/x-www-form-urlencoded\r\n"
                        + "Content-Length: "
                        + "userId=javajigi&password=password&nickname=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net".length()
                        + "\r\n"
                        + "\r\n"
                        + "userId=javajigi&password=password&nickname=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
        HttpRequest httpRequest = new HttpRequest(clientInput);

        // when
        signUpRequestHandler.handlePost(httpRequest,
                new HttpResponse(DataOutputStream.nullOutputStream(), httpRequest.getHttpVersion()));
        User user = (User) InMemoryDataBase.findById(1);

        // then
        assertAll(
                () -> assertThat(user.getId()).isEqualTo(1L),
                () -> assertThat(user.getUserId()).isEqualTo("javajigi"),
                () -> assertThat(user.getNickname()).isEqualTo("박재성"),
                () -> assertThat(user.getPassword()).isEqualTo("password"),
                () -> assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net")
        );
    }
}
