package codesquad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

            Map<String, String> requestLines = parseRequestLine(requestReader);
            FileInputStream fileInputStream = new FileInputStream(
                    "src/main/resources/static" + requestLines.get("requestUrl"));
            Map<String, String> requestHeaderFields = parseRequestHeaderFields(requestReader);
            String requestMessageBody = parseRequestMessageBody(requestReader,
                    requestHeaderFields.get("Content-Length"));

            log.debug("requestLines = {}", requestLines);
            log.debug("requestHeaderFields = {}", requestHeaderFields);
            log.debug("requestMessageBody = {}", requestMessageBody);
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

    private Map<String, String> parseRequestLine(final BufferedReader requestReader) throws IOException {
        String requestLine = requestReader.readLine();
        String[] splitLines = requestLine.split(" ");
        return Map.of(
                "httpMethod", splitLines[0],
                "requestUrl", splitLines[1],
                "httpVersion", splitLines[2]
        );
    }

    private Map<String, String> parseRequestHeaderFields(final BufferedReader requestReader) throws IOException {
        Map<String, String> requestHeaderFields = new HashMap<>();

        String headerLine;
        while (!(headerLine = requestReader.readLine()).isEmpty()) {
            String[] headerSplits = headerLine.split(":");
            requestHeaderFields.put(headerSplits[0], headerSplits[1]);
        }

        return requestHeaderFields;
    }

    private String parseRequestMessageBody(final BufferedReader bufferedReader,
                                           final String contentLengthValue) throws IOException {
        if (Objects.isNull(contentLengthValue)) {
            return "[Request Message Body is Empty]";
        }
        int contentLength = Integer.parseInt(contentLengthValue.trim());
        char[] buffer = new char[contentLength];
        int result = bufferedReader.read(buffer);
        if (result == 0) {
            return "[Can not Read Request Body]";
        }

        return String.valueOf(buffer);
    }
}
