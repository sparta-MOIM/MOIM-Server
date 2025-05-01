package com.sparta.moim.session.session.application.dto.result;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SearchSessionListResult(String title,
                                      UUID sessionId) {
}
