package codesquad.was.http;

import codesquad.was.http.type.StatusCodeType;

public class StatusLine {

    private final String httpVersion;
    private StatusCodeType responseStatus;

    public StatusLine(final String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHttpStatusCode() {
        return responseStatus.getStatusCode();
    }

    public void setResponseStatus(final StatusCodeType responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getHttpStatusMessage() {
        return responseStatus.getStatusMessage();
    }

    @Override
    public String toString() {
        return "StatusLine{" +
                "httpVersion='" + httpVersion + '\'' +
                ", statusCodeType=" + responseStatus +
                '}';
    }
}
