package com.sparta.moim.organization.infrastruct.dto;

import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrganizationMemberCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String organizationName;
    private String organizationTrackingId;
    private String userTrackingId;
    private String nickname;
    private OrganizationMemberRole role;

    public static OrganizationMemberCache from(OrganizationMember member) {
        return OrganizationMemberCache.builder()
                .organizationName(member.getOrganization().getOrganizationName())
                .organizationTrackingId(member.getOrganization().getTrackingId().toString())
                .userTrackingId(member.getUserTrackingId().toString())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }
}