package codesquad.was.exception;

import codesquad.was.http.type.StatusCodeType;

public class BadRequestException extends CommonException {

    public BadRequestException(final String message) {
        super(message, StatusCodeType.BAD_REQUEST);
    }
}
