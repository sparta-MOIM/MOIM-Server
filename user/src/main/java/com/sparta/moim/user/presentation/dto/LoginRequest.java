package com.sparta.moim.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "ID는 필수 항목 입니다.")
    String username,

    @NotBlank(message = "비밃번호는 필수 항목 입니다.")
    String password
) {
}
