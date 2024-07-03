package codesquad;

import codesquad.http.Headers;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.ResponseMessageBody;
import codesquad.http.StatusLine;
import codesquad.http.type.HeaderType;
import codesquad.http.type.MimeType;
import codesquad.http.type.StatusCodeType;
import codesquad.utils.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Socket clientSocket;

    public ConnectionHandler(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (InputStream clientInput = clientSocket.getInputStream();
             OutputStream clientOutput = clientSocket.getOutputStream()
        ) {
            HttpRequest httpRequest = new HttpRequest(clientInput);

            log.debug("Http Request = {}", httpRequest);
            log.debug("Client connected");

            HttpResponse httpResponse = createResponse(StatusCodeType.OK, httpRequest);

            clientOutput.write(httpResponse.getResponseBytes());
        } catch (IOException e) {
            log.error("요청을 처리할 수 없습니다.", e);
        }
    }

    private HttpResponse createResponse(final StatusCodeType statusCodeType, final HttpRequest httpRequest)
            throws IOException {
        String requestPath = httpRequest.getRequestPath();

        URL fileUrl = getClass().getClassLoader().getResource("static" + httpRequest.getRequestPath());
        try (InputStream inputStream = fileUrl.openStream()) {
            Headers headers = new Headers();
            headers.add(HeaderType.CONTENT_TYPE, MimeType.findMimeValue(StringUtils.getFilenameExtension(requestPath)));

            return new HttpResponse(
                    new StatusLine(httpRequest.getHttpVersion(), statusCodeType),
                    headers,
                    new ResponseMessageBody(inputStream.readAllBytes()));
        }
    }
}
