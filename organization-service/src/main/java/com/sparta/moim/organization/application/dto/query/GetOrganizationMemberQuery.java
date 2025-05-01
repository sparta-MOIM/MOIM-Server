package com.sparta.moim.organization.application.dto.query;

import com.sparta.moim.organization.domain.entity.OrganizationMember;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetOrganizationMemberQuery {
    private String memberTrackingId;
    private String nickname;;
    private String organizationRole;
    private String userTrackingId;

    public static GetOrganizationMemberQuery from(OrganizationMember organizationMember){
        return GetOrganizationMemberQuery.builder()
                .memberTrackingId(organizationMember.getTrackingId().toString())
                .nickname(organizationMember.getNickname())
                .organizationRole(organizationMember.getRole().toString())
                .userTrackingId(organizationMember.getUserTrackingId().toString())
                .build();
    }
}
