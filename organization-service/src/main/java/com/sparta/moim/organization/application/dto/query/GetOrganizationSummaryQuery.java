package com.sparta.moim.organization.application.dto.query;

import com.sparta.moim.organization.domain.entity.Organization;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetOrganizationSummaryQuery {
    private String organizationTrackingId;
    private String organizationName;
    private Integer memberCount;

    public static GetOrganizationSummaryQuery from(Organization organization) {
        return GetOrganizationSummaryQuery.builder()
                .organizationTrackingId(organization.getTrackingId().toString())
                .organizationName(organization.getOrganizationName())
                .memberCount(organization.getOrganizationOrganizationMembers().size())
                .build();
    }
}
