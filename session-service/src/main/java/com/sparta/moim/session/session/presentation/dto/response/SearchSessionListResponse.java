package com.sparta.moim.session.session.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchSessionListResponse(
    String title,
    UUID publisher
) {
}
