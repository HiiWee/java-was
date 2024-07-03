package codesquad.http;

import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

public class HttpResponse {

    private static final String CRLF = "\r\n";
    private static final String ONE_SPACE = " ";
    private static final String DELIMITER = ": ";
    private static final String UTF_8 = "UTF-8";

    private final StatusLine statusLine;
    private final Headers headers;
    private final ResponseMessageBody responseBody;

    public HttpResponse(final StatusLine statusLine, final Headers headers, final ResponseMessageBody responseBody) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.responseBody = responseBody;
    }

    public byte[] getResponseBytes() throws UnsupportedEncodingException {
        byte[] responseBytesWithoutBody = (getStatusLineMessage() + CRLF + getHeaderMessage() + CRLF + CRLF).getBytes(
                UTF_8);
        byte[] responseBodyBytes = responseBody.getBytes();
        byte[] bytes = new byte[responseBytesWithoutBody.length + responseBodyBytes.length];
        System.arraycopy(responseBytesWithoutBody, 0, bytes, 0, responseBytesWithoutBody.length);
        System.arraycopy(responseBodyBytes, 0, bytes, responseBytesWithoutBody.length, responseBodyBytes.length);

        return bytes;
    }

    private String getStatusLineMessage() {
        return statusLine.getHttpVersion() + ONE_SPACE + statusLine.getHttpStatus() + ONE_SPACE
                + statusLine.getHttpStatusMessage();
    }

    private String getHeaderMessage() {
        return headers.getHeaders()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getHeaderName() + DELIMITER + entry.getValue())
                .collect(Collectors.joining(CRLF));
    }

    private byte[] createBodyMessage() {
        return responseBody.getBytes();
    }
}
