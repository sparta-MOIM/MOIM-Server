package com.sparta.moim.organization.application.dto.command;

import lombok.Getter;

@Getter
public class UpdateOrganizationCommand {

    private String updateOrganizationName;
    private String updateDescription;

}
