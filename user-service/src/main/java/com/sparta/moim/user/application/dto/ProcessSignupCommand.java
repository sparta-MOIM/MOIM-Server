package com.sparta.moim.user.application.dto;

public record ProcessSignupCommand(
    String username,
    String password,
    String name
) {

}
