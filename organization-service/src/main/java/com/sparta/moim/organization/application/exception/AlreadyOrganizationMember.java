package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.organization.presentation.code.OrganizationCode;

public class AlreadyOrganizationMember extends BaseException {
    public AlreadyOrganizationMember() {
        super(OrganizationCode.ALREADY_ORGANIZATION_MEMBER);
    }
}
