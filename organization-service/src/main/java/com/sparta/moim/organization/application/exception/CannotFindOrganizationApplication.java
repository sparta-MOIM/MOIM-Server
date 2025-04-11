package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.organization.presentation.code.OrganizationCode;

public class CannotFindOrganizationApplication extends BaseException {
    public CannotFindOrganizationApplication() {
        super(OrganizationCode.CANNOT_FIND_ORGANIZATION_APPLICATION);
    }
}
