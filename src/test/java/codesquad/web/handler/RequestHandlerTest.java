package codesquad.web.handler;

import codesquad.was.ContextHolder;
import codesquad.was.http.Headers;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.type.HeaderType;
import codesquad.web.io.InMemoryUserRepository;
import codesquad.web.io.UserRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

public class RequestHandlerTest {

    protected UserRepository userRepository;

    @BeforeEach
    void cleanStorage() {
        userRepository = new InMemoryUserRepository();
        ContextHolder.clear();
    }

    protected void 회원가입을_한다() throws IOException {
        SignUpRequestHandler signUpRequestHandler = new SignUpRequestHandler(new InMemoryUserRepository());
        String httpRequestValue =
                "POST /user/create HTTP/1.1\r\n"
                        + "Host: localhost\r\n"
                        + "Connection: keep-alive\r\n"
                        + "Content-Type: application/x-www-form-urlencoded\r\n"
                        + "Content-Length: "
                        + "userId=test&nickname=nick&password=password&email=email%40email.com".getBytes().length
                        + "\r\n"
                        + "\r\n"
                        + "userId=test&nickname=nick&password=password&email=email%40email.com";
        InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
        HttpRequest request = new HttpRequest(clientInput);
        HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

        signUpRequestHandler.handlePost(request, response);
    }

    protected String 로그인을_한다() throws IOException {
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler(new InMemoryUserRepository());
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

        loginRequestHandler.handlePost(request, response);
        String sessionId = getUuid(response);
        ContextHolder.setContext(sessionId);

        return sessionId;
    }

    protected void 로그아웃을_한다(String sessionId) throws IOException {
        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
        String httpRequestValue =
                "GET /user/logout HTTP/1.1\r\n"
                        + "Host: localhost\r\n"
                        + "Cookie: sid=" + sessionId + "\r\n"
                        + "\r\n";
        InputStream clientInput = new ByteArrayInputStream(httpRequestValue.getBytes("UTF-8"));
        HttpRequest request = new HttpRequest(clientInput);
        HttpResponse response = new HttpResponse(OutputStream.nullOutputStream(), request.getHttpVersion());

        logoutRequestHandler.handleGet(request, response);
    }

    private String getUuid(final HttpResponse response) {
        Headers headers = response.getHeaders();
        List<String> cookies = headers.getHeader(HeaderType.SET_COOKIE);
        String[] split = cookies.get(0).split("; ");
        return split[0].split("=")[1];
    }
}
