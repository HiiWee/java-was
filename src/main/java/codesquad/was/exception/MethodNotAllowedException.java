package codesquad.was.exception;

import codesquad.was.http.type.StatusCodeType;

public class MethodNotAllowedException extends CommonException {

    public MethodNotAllowedException(final String message) {
        super(message, StatusCodeType.METHOD_NOT_ALLOWED);
    }
}
