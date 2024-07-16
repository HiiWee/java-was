package codesquad.web.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import codesquad.was.exception.BadRequestException;
import codesquad.was.http.Headers;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.HeaderType;
import codesquad.web.domain.User;
import codesquad.web.handler.fixture.RequestHandlerTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LoginRequestHandlerTest extends RequestHandlerTest {

    @Test
    void 로그인을_할_수_있다() throws IOException {
        // given
        회원가입을_한다();
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler(userRepository);
        String httpRequestValue =
                "POST /user/login HTTP/1.1\r\n"
                        + "Host: localhost\r\n"
                        + "Connection: keep-alive\r\n"
                        + "Content-Type: application/x-www-form-urlencoded\r\n"
                        + "Content-Length: "
                        + "userId=test&password=password".getBytes().length
                        + "\r\n"
                        + "\r\n"
                        + "userId=test&password=password";
        InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
        HttpRequest request = new HttpRequest(clientInput);
        HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

        // when
        loginRequestHandler.handlePost(request, response);
        String uuid = getUuid(response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(uuid);

        // then
        assertAll(
                () -> assertThat(user.getUserId()).isEqualTo("test"),
                () -> assertThat(user.getNickname()).isEqualTo("nick"),
                () -> assertThat(user.getPassword()).isEqualTo("password"),
                () -> assertThat(user.getEmail()).isEqualTo("email@email.com")
        );
    }

    @Nested
    class 로그인_정보가_하나라도_존재하지_않으면 {

        @Test
        void 예외가_발생한다() throws IOException {
            // given
            회원가입을_한다();
            LoginRequestHandler loginRequestHandler = new LoginRequestHandler(userRepository);
            String httpRequestValue =
                    "POST /user/login HTTP/1.1\r\n"
                            + "Host: localhost\r\n"
                            + "Connection: keep-alive\r\n"
                            + "Content-Type: application/x-www-form-urlencoded\r\n"
                            + "Content-Length: "
                            + "userId=&password=password".getBytes().length
                            + "\r\n"
                            + "\r\n"
                            + "userId=&password=password";
            InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
            HttpRequest request = new HttpRequest(clientInput);
            HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

            // expect
            assertThatThrownBy(() -> loginRequestHandler.handlePost(request, response))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("사용자의 정보를 찾을 수 없습니다. userId = null, password = password");
        }
    }

    private String getUuid(final HttpResponse response) {
        Headers headers = response.getHeaders();
        List<String> cookies = headers.getHeader(HeaderType.SET_COOKIE);
        String[] split = cookies.get(0).split("; ");
        return split[0].split("=")[1];
    }
}
