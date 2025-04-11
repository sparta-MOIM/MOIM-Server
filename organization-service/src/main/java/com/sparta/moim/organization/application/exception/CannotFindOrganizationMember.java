package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.organization.presentation.code.OrganizationCode;

public class CannotFindOrganizationMember extends BaseException {
    public CannotFindOrganizationMember() {
      super(OrganizationCode.CANNOT_FIND_ORGANIZATION_MEMBER);
    }
}
