package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.organization.presentation.code.OrganizationCode;

public class OrganizationMasterRequiredException extends BaseException {
    public OrganizationMasterRequiredException(){
        super(OrganizationCode.NOT_ORGANIZATION_MASTER);
    }
}
