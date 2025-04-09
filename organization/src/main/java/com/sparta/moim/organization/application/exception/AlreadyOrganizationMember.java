package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class AlreadyOrganizationMember extends BaseException {
    public AlreadyOrganizationMember() {
        super(Code.ORGANIZATION_ALREADY_MEMBER);
    }
}
