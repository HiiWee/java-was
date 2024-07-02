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

    private final Logger log = LoggerFactory.getLogger(Main.class);

    private final Socket clientSocket;

    public ConnectionHandler(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             OutputStream clientOutput = clientSocket.getOutputStream();
             BufferedReader requestReader = new BufferedReader(inputStreamReader)
        ) {
            HttpRequest httpRequest = new HttpRequest(requestReader);
            String requestPath = httpRequest.getRequestPath();
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/static" + requestPath);

            log.debug("Http Request = {}", httpRequest);
            log.debug("Client connected");

            Headers headers = new Headers();
            headers.add(HeaderType.CONTENT_TYPE, MimeType.findMimeValue(StringUtils.getFilenameExtension(requestPath)));
            HttpResponse httpResponse = new HttpResponse(
                    new StatusLine("HTTP/1.1", StatusCodeType.OK),
                    headers,
                    new MessageBody(fileInputStream.readAllBytes()));

            httpResponse.createResponseMessage();
            clientOutput.write(httpResponse.createResponseMessage().getBytes());
            fileInputStream.close();
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
}
