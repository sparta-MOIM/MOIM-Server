package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.organization.presentation.code.OrganizationCode;

public class MemberAccessDeniedException extends BaseException {
    public MemberAccessDeniedException() {
        super(OrganizationCode.MEMBER_ACCESS_DENIED);
    }
}
