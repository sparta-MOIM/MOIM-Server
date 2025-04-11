package com.sparta.moim.organization.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ApplyOrganizationRequest {

    @NotBlank(message = "신청서 내용은 필수입니다.")
    @Size(max = 100, message = "신청서 내용은 최대 100자까지 가능합니다.")
    private String content;

}
