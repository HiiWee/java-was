package codesquad.was.http;

import codesquad.was.http.type.HttpMethod;

public class RequestLine {

    private final HttpMethod method;
    private final String requestPath;
    private final String httpVersion;

    public RequestLine(final HttpMethod method, final String requestPath, final String httpVersion) {
        this.method = method;
        this.requestPath = requestPath;
        this.httpVersion = httpVersion;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}
