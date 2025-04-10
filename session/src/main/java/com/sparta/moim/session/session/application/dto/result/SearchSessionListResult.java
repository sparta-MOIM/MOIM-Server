package com.sparta.moim.session.session.application.dto.result;

import lombok.Builder;

@Builder
public record SearchSessionListResult(String title,
                                      String publisher) {
}
