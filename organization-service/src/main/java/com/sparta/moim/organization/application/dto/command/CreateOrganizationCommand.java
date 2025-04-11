package com.sparta.moim.organization.application.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrganizationCommand {
    private String organizationName;
    private String description;
    private String nickname;
}
