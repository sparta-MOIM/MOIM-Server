package com.sparta.moim.session.shared.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public final class SharedSessionMember {
  private String memberName;
  private UUID sessionId;
  private String type;
}
