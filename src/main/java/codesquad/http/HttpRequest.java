package codesquad.http;

import codesquad.http.type.HeaderType;
import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final MessageBody requestBody;

    public HttpRequest(final RequestLine requestLine, final Headers headers, final MessageBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public HttpRequest(final BufferedReader requestReader) throws IOException {
        requestLine = new RequestLine(requestReader.readLine());
        headers = new Headers(requestReader);
        requestBody = new MessageBody(requestReader, headers.getHeader(HeaderType.CONTENT_LENGTH));
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
                ", body=" + requestBody +
                '}';
    }
}
