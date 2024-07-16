package codesquad.was.exception;

import codesquad.was.http.type.StatusCodeType;

public class InternalServerException extends CommonException {

    private static final String MESSAGE = "서버에서 요청을 처리할 수 없습니다.";

    public InternalServerException() {
        super(MESSAGE, StatusCodeType.INTERNAL_SERVER_ERROR);
    }
}
