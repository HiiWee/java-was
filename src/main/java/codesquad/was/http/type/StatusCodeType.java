package codesquad.was.http.type;

public enum StatusCodeType {

    OK("200", "OK"),
    FOUND("302", "Found"),
    BAD_REQUEST("400", "Bad Request"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    ;

    private final String statusCode;
    private final String statusMessage;

    StatusCodeType(final String statusCode, final String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
