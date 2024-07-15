package codesquad.web.handler;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class LogoutRequestHandlerTest extends RequestHandlerTest {

    private String sessionId;

    @AfterEach
    void tearDown() throws IOException {
        로그아웃을_한다(sessionId);
    }

    @Test
    void 로그아웃을_할_수_있다() throws IOException {
        // given
        회원가입을_한다();
        sessionId = 로그인을_한다();
        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
        String httpRequestValue =
                "GET /user/logout HTTP/1.1\r\n"
                        + "Host: localhost\r\n"
                        + "Connection: keep-alive\r\n"
                        + "Cookie: sid=" + this.sessionId + "\r\n"
                        + "\r\n";
        InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
        HttpRequest request = new HttpRequest(clientInput);
        HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

        // when
        logoutRequestHandler.handleGet(request, response);
        HttpSession session = request.getSession();

        // then
        assertThat(session.getAttribute(this.sessionId)).isNull();
    }
}
