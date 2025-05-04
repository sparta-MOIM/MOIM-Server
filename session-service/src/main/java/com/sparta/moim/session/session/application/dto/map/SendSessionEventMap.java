package com.sparta.moim.session.session.application.dto.map;

import com.sparta.moim.session.shared.enums.MemberType;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

@Builder
public record SendSessionEventMap(
    String sessionId,
    String memberId,
    MemberType type
) {

  public Map<String, String> toMap() {
    Map<String, String> result = new HashMap<>();
    result.put("sessionId", sessionId);
    result.put("memberId", memberId);
    result.put("type", type.name());
    return result;
  }
}
