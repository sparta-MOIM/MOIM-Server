package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.organization.presentation.code.OrganizationCode;

public class AlreadyUsedNickname extends BaseException {
    public AlreadyUsedNickname() {
        super(OrganizationCode.ALREADY_USED_ORGANIZATION_NICKNAME);
    }
}
