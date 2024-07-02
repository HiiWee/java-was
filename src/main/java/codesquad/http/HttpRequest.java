package codesquad.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final MessageBody body;

    public HttpRequest(final RequestLine requestLine, final Headers headers, final MessageBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpRequest(final BufferedReader requestReader) throws IOException {
        requestLine = new RequestLine(requestReader.readLine());
        headers = new Headers(requestReader);
        body = new MessageBody(requestReader, headers.getHeader("Content-Length"));
    }

    public String getRequestPath() {
        return requestLine.getRequestPath();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", body=" + body +
                '}';
    }
}
