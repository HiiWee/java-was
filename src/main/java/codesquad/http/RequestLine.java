package codesquad.http;

public class RequestLine {

    private static final String ONE_SPACE = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int VERSION_INDEX = 2;

    private final String method;
    private final String requestPath;
    private final String httpVersion;

    public RequestLine(final String method, final String requestPath, final String httpVersion) {
        this.method = method;
        this.requestPath = requestPath;
        this.httpVersion = httpVersion;
    }

    public RequestLine(final String requestLine) {
        String[] splitLines = requestLine.split(ONE_SPACE);
        method = splitLines[METHOD_INDEX];
        requestPath = splitLines[URL_INDEX];
        httpVersion = splitLines[VERSION_INDEX];
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
                ", requestTarget='" + requestPath + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}
