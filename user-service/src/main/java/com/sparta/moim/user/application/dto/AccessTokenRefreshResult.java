package com.sparta.moim.user.application.dto;

import org.springframework.http.ResponseCookie;

public record AccessTokenRefreshResult(
    ResponseCookie accessTokenCookie
) {
}
