package codesquad.http;

import java.util.stream.Collectors;

public class HttpResponse {

    private static final String CRLF = "\r\n";
    private static final String ONE_SPACE = " ";
    private static final String DELIMITER = ": ";

    private final StatusLine statusLine;
    private final Headers headers;
    private final MessageBody responseBody;

    public HttpResponse(final StatusLine statusLine, final Headers headers, final MessageBody responseBody) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.responseBody = responseBody;
    }

    public String createResponseMessage() {
        return createStatusLineMessage() + CRLF
                + createHeaderMessage() + CRLF + CRLF
                + createBodyMessage();
    }

    private String createStatusLineMessage() {
        return statusLine.getHttpVersion() + ONE_SPACE + statusLine.getHttpStatus() + ONE_SPACE
                + statusLine.getHttpStatusMessage();
    }

    private String createHeaderMessage() {
        return headers.getHeaders()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getHeaderName() + DELIMITER + entry.getValue())
                .collect(Collectors.joining(CRLF));
    }

    private String createBodyMessage() {
        return responseBody.getBodyData();
    }
}
