package codesquad.was.exception;

import codesquad.was.http.type.StatusCodeType;

public class NotFoundException extends CommonException {

    public NotFoundException(final String message) {
        super(message, StatusCodeType.NOT_FOUND);
    }
}
