package com.sparta.moim.organization.application.dto.query;

import com.sparta.moim.organization.domain.entity.Organization;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetOrganizationQuery {

    private String organizationTrackingId;
    private String organizationName;
    private String description;
    private Integer memberCount;

    public static GetOrganizationQuery from(Organization organization) {
        return GetOrganizationQuery.builder()
                .organizationTrackingId(organization.getTrackingId().toString())
                .organizationName(organization.getOrganizationName())
                .description(organization.getDescription())
                .memberCount(organization.getOrganizationOrganizationMembers().size())
                .build();
    }
}
