package codesquad.was.http;

import codesquad.was.http.type.HeaderType;
import codesquad.was.http.type.HttpMethod;
import codesquad.was.http.type.MimeType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpRequest {

    private static final String ONE_SPACE = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;
    private static final int KEY_VALUE_LENGTH = 2;

    private final RequestLine requestLine;
    private final Headers headers;
    private final Map<String, String> requestParameters = new HashMap<>();
    private final RequestMessageBody requestBody;

    public HttpRequest(final RequestLine requestLine, final Headers headers, final RequestMessageBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public HttpRequest(final InputStream clientInput) throws IOException {
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientInput));
        requestLine = createRequestLine(requestReader.readLine());
        headers = new Headers(requestReader);
        requestBody = new RequestMessageBody(requestReader, headers.getHeader(HeaderType.CONTENT_LENGTH));

        if (isFormData()) {
            String bodyData = requestBody.getBodyData();
            requestParameters.putAll(parseQueryParams(bodyData));
        }
    }

    private boolean isFormData() {
        String contentType = headers.getHeader(HeaderType.CONTENT_TYPE);

        if (Objects.isNull(contentType)) {
            return false;
        }
        return contentType.contains(MimeType.APPLICATION_X_WWW_FORM_ENCODED.getValue());
    }

    private RequestLine createRequestLine(final String requestLine) throws UnsupportedEncodingException {
        String[] splitLines = requestLine.split(ONE_SPACE);
        HttpMethod method = HttpMethod.find(splitLines[METHOD_INDEX].toUpperCase());
        String[] pathWithQueryString = splitDecodedQueryString(splitLines[URI_INDEX]);
        String requestPath = pathWithQueryString[PATH_INDEX];
        if (pathWithQueryString.length == 2) {
            requestParameters.putAll(parseQueryParams(pathWithQueryString[QUERY_STRING_INDEX]));
        }
        String httpVersion = splitLines[VERSION_INDEX];

        return new RequestLine(method, requestPath, httpVersion);
    }

    private String[] splitDecodedQueryString(final String queryString) throws UnsupportedEncodingException {
        String decodedQueryString = URLDecoder.decode(queryString, "UTF-8");

        return decodedQueryString.split("\\?");
    }

    private Map<String, String> parseQueryParams(final String queryString) {
        return Arrays.stream(queryString.split("&"))
                .map(param -> param.split("="))
                .filter(params -> params.length == KEY_VALUE_LENGTH)
                .collect(Collectors.toMap(splitParams -> splitParams[0], splitParams -> splitParams[1]));
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

    public String getParameter(final String name) {
        return requestParameters.get(name);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", requestParameters=" + requestParameters +
                ", requestBody=" + requestBody +
                '}';
    }
}
