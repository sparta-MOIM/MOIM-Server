package com.sparta.moim.organization.application.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateOrganizationCommand {

    private String updateOrganizationName;
    private String updateDescription;

}
