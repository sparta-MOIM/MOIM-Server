package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class CannotFindOrganization extends BaseException {
    public CannotFindOrganization() {
        super(Code.ORGANIZATION_CANNOT_FIND_ORGANIZATION);
    }
}
