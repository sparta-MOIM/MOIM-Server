package com.sparta.moim.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupUserRequest(
    @NotBlank(message = "유저네임은 필수 항목입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유저네임은 소문자와 숫자로 4~10자 사이여야 합니다.")
    String username,

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}|:\"<>?\\[\\]\\\\/]).{8,15}$",
        message = "비밀번호는 대소문자, 숫자, 특수문자를 포함한 8~15자 사이여야 합니다.")
    String password,

    @NotBlank(message = "이름은 필수 항목입니다.")
    String name

    ) {
}
