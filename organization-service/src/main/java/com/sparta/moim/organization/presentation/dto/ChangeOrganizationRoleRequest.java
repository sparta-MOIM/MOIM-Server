package com.sparta.moim.organization.presentation.dto;

import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeOrganizationRoleRequest {

    @NotNull(message = "권한을 입력해주세요.")
    private OrganizationMemberRole role;

    @NotBlank(message = "멤버의 고유 id를 입력해주세요.")
    private String memberTrackingId;
}
