package com.sparta.moim.session.session.presentation.dto.response;

import lombok.Builder;

@Builder
public record SearchSessionListResponse(
    String title,
    String publisher
) {
}
