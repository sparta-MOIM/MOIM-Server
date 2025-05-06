package com.sparta.moim.session.session.application.dto.context;

import com.sparta.moim.session.session.application.dto.map.SendSessionEventMap;
import lombok.Builder;

@Builder
public record SessionRedisExecutionContext(SendSessionEventMap event, String scriptName, String lockKey, String streamKey) {
}
