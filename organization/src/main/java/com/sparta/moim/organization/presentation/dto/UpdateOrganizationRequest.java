package com.sparta.moim.organization.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateOrganizationRequest {

    @NotBlank(message = "조직 이름은 필수입니다.")
    @Size(max = 50, message = "조직 이름은 50자 이하로 입력해주세요.")
    private String updateOrganizationName;

    @NotBlank(message = "조직 설명은 필수입니다.")
    @Size(max = 100, message = "조직 설명은 최대 100자까지 가능합니다.")
    private String updateOrganizationDescription;

}
