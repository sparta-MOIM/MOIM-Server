package com.sparta.moim.organization.infrastruct.dto;

import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationMemberCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String organizationName;
    private String organizationTrackingId;
    private String userTrackingId;
    private String nickname;
    private OrganizationMemberRole role;
}