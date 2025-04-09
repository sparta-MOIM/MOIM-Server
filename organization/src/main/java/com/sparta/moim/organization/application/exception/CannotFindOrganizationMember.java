package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class CannotFindOrganizationMember extends BaseException {
    public CannotFindOrganizationMember() {
      super(Code.ORGANIZATION_CANNOT_FIND_ORGANIZATION_MEMBER);
    }
}
