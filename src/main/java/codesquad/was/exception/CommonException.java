package codesquad.was.exception;

import codesquad.was.http.type.StatusCodeType;

public class CommonException extends RuntimeException {

    private final String message;
    private final StatusCodeType statusCodeType;

    public CommonException(final String message, final StatusCodeType statusCodeType) {
        super(message);
        this.message = message;
        this.statusCodeType = statusCodeType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public StatusCodeType getStatusCodeType() {
        return statusCodeType;
    }
}
