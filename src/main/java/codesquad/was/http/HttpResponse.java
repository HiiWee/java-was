package codesquad.was.http;

import codesquad.utils.StringUtils;
import codesquad.was.http.type.HeaderType;
import codesquad.was.http.type.MimeType;
import codesquad.was.http.type.StatusCodeType;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.stream.Collectors;

public class HttpResponse {

    private static final String CRLF = "\r\n";
    private static final String ONE_SPACE = " ";
    private static final String DELIMITER = ": ";

    private final StatusLine statusLine;
    private final Headers headers = new Headers();
    private final DataOutputStream dataOutputStream;

    public HttpResponse(final OutputStream outputStream, final String httpVersion) {
        this.statusLine = new StatusLine(httpVersion);
        this.dataOutputStream = new DataOutputStream(outputStream);
    }

    public void forward(final String requestPath) throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource("static" + requestPath);

        if (fileUrl == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다. requestPath = " + requestPath);
        }

        try (InputStream inputStream = fileUrl.openStream()) {
            byte[] fileBytes = inputStream.readAllBytes();
            headers.add(HeaderType.CONTENT_TYPE, MimeType.findMimeValue(StringUtils.getFilenameExtension(requestPath)));
            headers.add(HeaderType.CONTENT_LENGTH, String.valueOf(fileBytes.length));
            statusLine.setResponseStatus(StatusCodeType.OK);

            sendResponse(fileBytes);
        }
    }

    private void sendResponse(final byte[] responseBytes) throws IOException {
        dataOutputStream.writeBytes(getStatusLineMessage() + CRLF + getHeaderMessage() + CRLF + CRLF);
        dataOutputStream.write(responseBytes);
    }

    private String getStatusLineMessage() {
        return statusLine.getHttpVersion() + ONE_SPACE + statusLine.getHttpStatusCode() + ONE_SPACE
                + statusLine.getHttpStatusMessage();
    }

    private String getHeaderMessage() {
        return headers.getHeaders()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().getHeaderName() + DELIMITER + entry.getValue())
                .collect(Collectors.joining(CRLF));
    }

    public void sendRedirect(final String redirectPath) throws IOException {
        statusLine.setResponseStatus(StatusCodeType.FOUND);
        headers.add(HeaderType.LOCATION, redirectPath);
        sendResponse(new byte[0]);
    }
}
