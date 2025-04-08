package com.sparta.moim.organization.application.dto.command;

import lombok.Getter;

@Getter
public class CreateOrganizationCommand {
    private String organizationName;
    private String description;
}
