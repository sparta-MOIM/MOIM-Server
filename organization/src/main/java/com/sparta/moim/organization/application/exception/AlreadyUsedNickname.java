package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class AlreadyUsedNickname extends BaseException {
    public AlreadyUsedNickname() {
        super(Code.ORGANIZATION_ALREADY_USED_NICKNAME);
    }
}
