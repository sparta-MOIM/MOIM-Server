package com.sparta.moim.gathering.gathering.domain.dto.criteria;


import com.sparta.moim.gathering.shared.enums.EventType;
import com.sparta.moim.gathering.shared.enums.OutboxType;
import lombok.Builder;

@Builder
public record GatheringEventCriteria(String streamKey, EventType eventType, String payload, OutboxType outboxType) {
}
