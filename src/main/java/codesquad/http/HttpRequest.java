package codesquad.http;

import codesquad.http.type.HeaderType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestMessageBody requestBody;

    public HttpRequest(final RequestLine requestLine, final Headers headers, final RequestMessageBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public HttpRequest(final InputStream clientInput) throws IOException {
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientInput));
        requestLine = new RequestLine(requestReader.readLine());
        headers = new Headers(requestReader);
        requestBody = new RequestMessageBody(requestReader, headers.getHeader(HeaderType.CONTENT_LENGTH));
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
