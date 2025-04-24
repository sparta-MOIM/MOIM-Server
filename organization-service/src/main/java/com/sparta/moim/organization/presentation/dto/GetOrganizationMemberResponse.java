package com.sparta.moim.organization.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrganizationMemberResponse {
    private String memberTrackingId;
    private String nickname;
    private String organizationRole;
}
