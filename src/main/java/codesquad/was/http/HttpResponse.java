package codesquad.was.http;

import static codesquad.was.http.type.CharsetType.UTF_8;

import codesquad.utils.StringUtils;
import codesquad.was.http.type.HeaderType;
import codesquad.was.http.type.MimeType;
import codesquad.was.http.type.StatusCodeType;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

    private static final int ONE_MB = 1048576;
    private static final String CRLF = "\r\n";

    private final StatusLine statusLine;
    private final Headers headers = new Headers();
    private final List<Cookie> cookies = new ArrayList<>();
    private final BufferedOutputStream bufferedOutputStream;

    public HttpResponse(final OutputStream outputStream, final String httpVersion) {
        this.statusLine = new StatusLine(httpVersion);
        this.bufferedOutputStream = new BufferedOutputStream(outputStream, ONE_MB);
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

    public void sendRedirect(final String redirectPath) throws IOException {
        statusLine.setResponseStatus(StatusCodeType.FOUND);
        headers.add(HeaderType.LOCATION, redirectPath);
        sendResponse(new byte[0]);
    }

    public void sendError(final StatusCodeType statusCodeType) throws IOException {
        statusLine.setResponseStatus(statusCodeType);
        sendResponse(new byte[0]);
    }

    private void sendResponse(final byte[] responseBytes) throws IOException {
        if (!cookies.isEmpty()) {
            headers.addCookies(cookies);
        }
        String statusLineMessage = statusLine.createMessage();
        String headersMessage = headers.createMessage();

        bufferedOutputStream.write((statusLineMessage + CRLF).getBytes(UTF_8.getCharset()));
        if (!headersMessage.isEmpty()) {
            bufferedOutputStream.write((headersMessage + CRLF).getBytes(UTF_8.getCharset()));
        }
        bufferedOutputStream.write(CRLF.getBytes(UTF_8.getCharset()));
        if (responseBytes.length > 0) {
            bufferedOutputStream.write(responseBytes);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    public void addCookie(final Cookie cookie) {
        cookies.add(cookie);
    }
}
