package com.sparta.moim.organization.application.exception;

import com.sparta.moim.common.exception.BaseException;
import com.sparta.moim.common.response.Code;

public class OrganizationMasterRequiredException extends BaseException {
    public OrganizationMasterRequiredException(){
        super(Code.ORGANIZATION_NOT_MASTER);
    }
}
