package codesquad.was;

import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Socket clientSocket;
    private final RequestHandlerMapping requestHandlerMapping;

    public ConnectionHandler(final Socket clientSocket, final RequestHandlerMapping requestHandlerMapping) {
        this.clientSocket = clientSocket;
        this.requestHandlerMapping = requestHandlerMapping;
    }

    @Override
    public void run() {
        try (InputStream clientInput = clientSocket.getInputStream();
             OutputStream clientOutput = clientSocket.getOutputStream()
        ) {
            log.debug("Client connected");

            HttpRequest httpRequest = new HttpRequest(clientInput);
            log.debug("Http Request = {}", httpRequest);

            HttpResponse httpResponse = new HttpResponse(clientOutput, httpRequest.getHttpVersion());

            RequestHandler requestHandler = requestHandlerMapping.read(httpRequest.getRequestPath());
            requestHandler.process(httpRequest, httpResponse);
        } catch (IOException e) {
            log.error("요청을 처리할 수 없습니다.", e);
        }
    }

}
