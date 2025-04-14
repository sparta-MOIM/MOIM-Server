package com.sparta.moim.common.exception;

import static com.sparta.moim.common.response.CommonCode.INTERNAL_SERVER_ERROR;

import com.sparta.moim.common.response.Code;
import lombok.Getter;

@Getter
// 일괄된 커스텀 에러를 생성해주기 위한 BaseException
public class BaseException extends RuntimeException {

    private final Code code;

    // 기본 생성자에서는 일반적인 에러 코드를 사용
    public BaseException() {
        super(INTERNAL_SERVER_ERROR.getMessage());
        this.code = INTERNAL_SERVER_ERROR;
    }

    // 에러 메시지를 받는 생성자
    public BaseException(String message) {
        super(message);
        this.code = INTERNAL_SERVER_ERROR;
    }

    // 에러 메시지와 원인을 받는 생성자
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = INTERNAL_SERVER_ERROR;
    }

    // 원인만을 받는 생성자
    public BaseException(Throwable cause) {
        super(cause);
        this.code = INTERNAL_SERVER_ERROR;
    }

    // 에러 코드를 지정하는 생성자
    public BaseException(Code code) {
        super(code.getMessage());
        this.code = code;
    }

    // 에러 코드와 메시지를 받는 생성자
    public BaseException(Code code, String message) {
        super(message);
        this.code = code;
    }

    // 에러 코드, 메시지, 원인을 받는 생성자
    public BaseException(Code code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    // 에러 코드와 원인을 받는 생성자
    public BaseException(Code code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
