package codesquad.was.http.type;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static HttpMethod find(final String methodName) {
        return valueOf(methodName);
    }
}
