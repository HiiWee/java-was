package codesquad.was.http;

import static codesquad.was.http.type.CharsetType.UTF_8;

import codesquad.was.http.type.HeaderType;
import codesquad.was.http.type.HttpMethod;
import codesquad.was.http.type.MimeType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HttpRequest {

    private static final int TWO_MB = 2 * 1024 * 1024;
    private static final String ONE_SPACE = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;

    private static HttpSession httpSession;

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestParameters queryParameters = new RequestParameters();
    private final RequestMessageBody requestBody;

    public HttpRequest(final RequestLine requestLine, final Headers headers, final RequestMessageBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public HttpRequest(final InputStream clientInput) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(clientInput, TWO_MB);
        RequestInputStreamReader reader = new RequestInputStreamReader(bufferedInputStream);
        String requestLineValue = reader.readRequestLine();
        requestLine = createRequestLine(requestLineValue);

        List<String> headerValues = reader.readHeaders();
        headers = new Headers(headerValues);

        String contentLengthValue = parseContentLength();
        byte[] bodyBytes = reader.readBody(Integer.parseInt(contentLengthValue));

        MimeType mimeType = headers.getMimeType();
        requestBody = new RequestMessageBody(bodyBytes, mimeType, () -> headers.getHeader(HeaderType.CONTENT_TYPE));
    }

    private RequestLine createRequestLine(final String requestLineValue) throws UnsupportedEncodingException {
        String[] splitLines = requestLineValue.split(ONE_SPACE);
        HttpMethod method = HttpMethod.find(splitLines[METHOD_INDEX].toUpperCase());
        String[] pathWithQueryString = splitDecodedQueryString(splitLines[URI_INDEX]);
        String requestPath = pathWithQueryString[PATH_INDEX];

        if (pathWithQueryString.length == RequestParameters.KEY_VALUE_LENGTH) {
            queryParameters.putParameters(pathWithQueryString[QUERY_STRING_INDEX]);
        }
        String httpVersion = splitLines[VERSION_INDEX];

        return new RequestLine(method, requestPath, httpVersion);
    }

    private String parseContentLength() {
        List<String> headerValues = headers.getHeader(HeaderType.CONTENT_LENGTH);
        if (Objects.isNull(headerValues) || headerValues.isEmpty()) {
            return "0";
        }
        return headerValues.get(0);
    }

    private String[] splitDecodedQueryString(final String queryString) throws UnsupportedEncodingException {
        String decodedQueryString = URLDecoder.decode(queryString, UTF_8.getCharset());

        return decodedQueryString.split("\\?");
    }

    public String getParameter(final String name) {
        if (queryParameters.contains(name)) {
            return queryParameters.get(name);
        }
        if (requestBody.containsParameter(name)) {
            return requestBody.getParameter(name);
        }
        return null;
    }

    public List<Cookie> getCookies() {
        List<String> cookies = headers.getHeader(HeaderType.COOKIE);

        if (Objects.isNull(cookies) || cookies.isEmpty()) {
            return Collections.emptyList();
        }

        return cookies.stream()
                .map(cookie -> cookie.split("="))
                .map(keyAndValue -> new Cookie(keyAndValue[0], keyAndValue[1]))
                .toList();
    }

    public HttpSession getSession() {
        if (httpSession == null) {
            httpSession = new HttpSession();
        }
        return httpSession;
    }

    public HttpSession getSession(final boolean needCreate) {
        if (needCreate && httpSession == null) {
            return httpSession = new HttpSession();
        }
        return httpSession;
    }

    public String getRequestPath() {
        return requestLine.getRequestPath();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getMethod();
    }

    public MultipartFile getMultipartFile() {
        return requestBody.getMultipartFile();
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", parameters=" + queryParameters +
                ", requestBody=" + requestBody +
                '}';
    }
}
