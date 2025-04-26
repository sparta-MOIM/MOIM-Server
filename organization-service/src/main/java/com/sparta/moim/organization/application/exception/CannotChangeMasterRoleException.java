package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.organization.presentation.code.OrganizationCode;

public class CannotChangeMasterRoleException extends BaseException {
    public CannotChangeMasterRoleException() {
        super(OrganizationCode.CANNOT_CHANGE_MASTER_ROLE);
    }
}
