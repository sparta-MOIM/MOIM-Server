package com.sparta.moim.gathering.gathering.application.dto.event.redis;

import com.sparta.moim.gathering.shared.enums.MemberType;
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
  private static final String KEY_GATHERING_ID = "gathering_id";
  private static final String KEY_MEMBER_NAME = "member_name";
  private static final String KEY_TYPE = "type";

  private UUID gatheringId;
  private String memberName;
  private MemberType type;

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put(KEY_GATHERING_ID, gatheringId.toString());
    map.put(KEY_MEMBER_NAME, memberName);
    map.put(KEY_TYPE, type.name());
    return map;
  }

  public static GatheringJoinAdminEvent fromMap(Map<String, String> map) {
    return new GatheringJoinAdminEvent(
        UUID.fromString(map.get(KEY_GATHERING_ID)),
        map.get(KEY_MEMBER_NAME),
        MemberType.valueOf(map.get(KEY_TYPE)));
  }
}
