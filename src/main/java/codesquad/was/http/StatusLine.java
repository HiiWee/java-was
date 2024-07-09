package codesquad.was.http;

import codesquad.was.http.type.StatusCodeType;

public class StatusLine {

    private static final String ONE_SPACE = " ";

    private final String httpVersion;
    private StatusCodeType responseStatus;

    public StatusLine(final String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String createMessage() {
        return httpVersion + ONE_SPACE + responseStatus.getStatusCode() + ONE_SPACE + responseStatus.getStatusMessage();
    }

    public void setResponseStatus(final StatusCodeType responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public String toString() {
        return "StatusLine{" +
                "httpVersion='" + httpVersion + '\'' +
                ", statusCodeType=" + responseStatus +
                '}';
    }
}
