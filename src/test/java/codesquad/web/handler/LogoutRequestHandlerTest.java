package codesquad.web.handler;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.model.User;
import codesquad.was.http.Headers;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import codesquad.was.http.type.HeaderType;
import codesquad.web.io.InMemoryUserDataBase;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogoutRequestHandlerTest {

    @BeforeEach
    void setUp() {
        long id = InMemoryUserDataBase.generateId();
        InMemoryUserDataBase.saveUser(new User(id, "test", "nick", "password", "email@email.com"));
    }

    @Test
    void 로그아웃을_할_수_있다() throws IOException {
        // given
        String sessionId = 로그인을_한다();
        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
        String httpRequestValue =
                "GET /user/logout HTTP/1.1\r\n"
                        + "Host: localhost\r\n"
                        + "Connection: keep-alive\r\n"
                        + "Cookie: sid=" + sessionId + "\r\n"
                        + "\r\n";
        InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
        HttpRequest request = new HttpRequest(clientInput);
        HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

        // when
        logoutRequestHandler.handleGet(request, response);
        HttpSession session = request.getSession();

        // then
        assertThat(session.getAttribute(sessionId)).isNull();
    }

    private String 로그인을_한다() throws IOException {
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();
        String httpRequestValue =
                "GET /create HTTP/1.1\r\n"
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

        loginRequestHandler.handlePost(request, response);
        return getUuid(response);
    }

    private String getUuid(final HttpResponse response) {
        Headers headers = response.getHeaders();
        List<String> cookies = headers.getHeader(HeaderType.SET_COOKIE);
        String[] split = cookies.get(0).split("; ");
        return split[0].split("=")[1];
    }
}
