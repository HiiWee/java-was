package codesquad;

import codesquad.http.HttpRequest;
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

            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            clientOutput.write("Content-Type: text/html\r\n".getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write(fileInputStream.readAllBytes());
            clientOutput.flush();
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
