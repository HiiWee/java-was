package codesquad.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestLine {

    private static final String ONE_SPACE = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;
    private static final int KEY_VALUE_LENGTH = 2;

    private final String method;
    private final String requestPath;
    private final Map<String, String> queryParams;
    private final String httpVersion;

    public RequestLine(final String method, final String requestPath, final Map<String, String> queryParams,
                       final String httpVersion) {
        this.method = method;
        this.requestPath = requestPath;
        this.queryParams = queryParams;
        this.httpVersion = httpVersion;
    }

    public RequestLine(final String requestLine) throws UnsupportedEncodingException {
        String[] splitLines = requestLine.split(ONE_SPACE);
        method = splitLines[METHOD_INDEX];
        String[] pathWithQueryString = splitDecodedQueryString(splitLines[URI_INDEX]);
        requestPath = pathWithQueryString[PATH_INDEX];
        queryParams = parseQueryParams(pathWithQueryString);
        httpVersion = splitLines[VERSION_INDEX];
    }

    private String[] splitDecodedQueryString(final String queryString) throws UnsupportedEncodingException {
        String decodedQueryString = URLDecoder.decode(queryString, "UTF-8");
        return decodedQueryString.split("\\?");
    }

    private Map<String, String> parseQueryParams(final String[] pathWithQueryString) {
        if (pathWithQueryString.length == 1) {
            return Collections.emptyMap();
        }

        return Arrays.stream(pathWithQueryString[QUERY_STRING_INDEX].split("&"))
                .map(param -> param.split("="))
                .filter(params -> params.length == KEY_VALUE_LENGTH)
                .collect(Collectors.toMap(splitParams -> splitParams[0], splitParams -> splitParams[1]));
    }

    public String getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Map<String, String> getQueryParams() {
        return Collections.unmodifiableMap(queryParams);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", queryParams=" + queryParams +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}
