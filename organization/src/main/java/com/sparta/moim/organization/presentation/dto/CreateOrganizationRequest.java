package com.sparta.moim.organization.presentation.dto;

import lombok.Getter;

@Getter
public class CreateOrganizationRequest {

    private String organizationName;
    private String description;
    private String nickname;
}
