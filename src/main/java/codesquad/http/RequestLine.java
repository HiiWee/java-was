package codesquad.http;

public class RequestLine {

    private final String method;
    private final String requestPath;
    private final String httpVersion;

    public RequestLine(final String method, final String requestPath, final String httpVersion) {
        this.method = method;
        this.requestPath = requestPath;
        this.httpVersion = httpVersion;
    }

    public String getMethod() {
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
