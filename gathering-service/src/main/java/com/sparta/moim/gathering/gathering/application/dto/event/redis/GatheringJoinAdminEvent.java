package com.sparta.moim.gathering.gathering.application.dto.event.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GatheringJoinAdminEvent {
  private UUID gatheringId;
  private String memberName;
  private String type;

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("gathering_id", gatheringId.toString());
    map.put("member_name", memberName);
    map.put("type", type);
    return map;
  }

  public static GatheringJoinAdminEvent fromMap(Map<String, String> map) {
    return new GatheringJoinAdminEvent(
        UUID.fromString(map.get("gathering_id")),
        map.get("member_name"),
        map.get("type")
    );
  }
}
