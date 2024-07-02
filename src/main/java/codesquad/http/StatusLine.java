package codesquad.http;

import codesquad.http.type.StatusCodeType;

public class StatusLine {

    private final String httpVersion;
    private final StatusCodeType statusCodeType;

    public StatusLine(final String httpVersion, final StatusCodeType statusCodeType) {
        this.httpVersion = httpVersion;
        this.statusCodeType = statusCodeType;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHttpStatus() {
        return statusCodeType.getStatusCode();
    }

    public String getHttpStatusMessage() {
        return statusCodeType.getStatusMessage();
    }
}
