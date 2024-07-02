package codesquad;

import codesquad.http.Headers;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.MessageBody;
import codesquad.http.StatusLine;
import codesquad.http.type.HeaderType;
import codesquad.http.type.MimeType;
import codesquad.http.type.StatusCodeType;
import codesquad.utils.StringUtils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandler implements Runnable {

    private static final String STATIC_RELATIVE_PATH = "src/main/resources/static";

    private final Logger log = LoggerFactory.getLogger(Main.class);
    private final Socket clientSocket;

    public ConnectionHandler(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader requestReader = new BufferedReader(inputStreamReader);
             OutputStream clientOutput = clientSocket.getOutputStream()
        ) {
            HttpRequest httpRequest = new HttpRequest(requestReader);

            log.debug("Http Request = {}", httpRequest);
            log.debug("Client connected");

            HttpResponse httpResponse = createResponse(StatusCodeType.OK, httpRequest);
            clientOutput.write(httpResponse.createResponseMessage().getBytes());
        } catch (IOException e) {
            log.error("요청을 처리할 수 없습니다.");
            throw new RuntimeException("요청을 처리할 수 없습니다.", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                log.error("클라이언트 소켓을 닫을 수 없습니다.");
                throw new RuntimeException(e);
            }
        }
    }

    private HttpResponse createResponse(final StatusCodeType statusCodeType, final HttpRequest httpRequest)
            throws IOException {
        String requestPath = httpRequest.getRequestPath();

        try (FileInputStream fileInputStream = new FileInputStream(STATIC_RELATIVE_PATH + requestPath)) {
            Headers headers = new Headers();
            headers.add(HeaderType.CONTENT_TYPE, MimeType.findMimeValue(StringUtils.getFilenameExtension(requestPath)));

            return new HttpResponse(
                    new StatusLine("HTTP/1.1", statusCodeType),
                    headers,
                    new MessageBody(fileInputStream.readAllBytes()));
        }
    }
}
