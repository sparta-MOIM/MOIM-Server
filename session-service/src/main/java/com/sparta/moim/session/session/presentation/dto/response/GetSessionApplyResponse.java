package com.sparta.moim.session.session.presentation.dto.response;

import java.time.LocalDateTime;

public record GetSessionApplyResponse(
    LocalDateTime applyTime,
    LocalDateTime confirmTime,
    String reason
) {
}

