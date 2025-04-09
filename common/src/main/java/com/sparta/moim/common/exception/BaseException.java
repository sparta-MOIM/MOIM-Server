package com.sparta.moim.common.exception;

import static com.sparta.moim.common.response.Code.INTERNAL_SERVER_ERROR;

import com.sparta.moim.common.response.BaseError;
import com.sparta.moim.common.response.Code;
import lombok.Getter;

@Getter
// 일괄된 커스텀 에러를 생성해주기 위한 BaseException
public class BaseException extends RuntimeException {

    private final BaseError baseError;

    // 기본 생성자에서는 일반적인 에러 코드를 사용
    public BaseException() {
        super(INTERNAL_SERVER_ERROR.getMessage());
        this.baseError = INTERNAL_SERVER_ERROR;
    }

    // 에러 메시지를 받는 생성자
    public BaseException(String message) {
        super(message);
        this.baseError = INTERNAL_SERVER_ERROR;
    }

    // 에러 메시지와 원인을 받는 생성자
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.baseError = INTERNAL_SERVER_ERROR;
    }

    // 원인만을 받는 생성자
    public BaseException(Throwable cause) {
        super(cause);
        this.baseError = INTERNAL_SERVER_ERROR;
    }

    // 에러 코드를 지정하는 생성자
    public BaseException(BaseError baseError) {
        super(baseError.getMessage());
        this.baseError = baseError;
    }

    // 에러 코드와 메시지를 받는 생성자
    public BaseException(BaseError baseError, String message) {
        super(message);
        this.baseError = baseError;
    }

    // 에러 코드, 메시지, 원인을 받는 생성자
    public BaseException(BaseError baseError, String message, Throwable cause) {
        super(message, cause);
        this.baseError = baseError;
    }

    // 에러 코드와 원인을 받는 생성자
    public BaseException(BaseError baseError, Throwable cause) {
        super(cause);
        this.baseError = baseError;
    }
}
