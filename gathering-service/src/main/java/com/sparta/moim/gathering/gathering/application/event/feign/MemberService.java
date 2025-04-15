package com.sparta.moim.gathering.gathering.application.event.feign;

import com.sparta.moim.gathering.gathering.application.dto.result.GetGatheringMemberListResult;
import java.util.List;
import java.util.UUID;

public interface MemberService {
  /**
 * 지정된 모임 ID에 해당하는 모든 멤버 목록을 조회합니다.
 *
 * @param gatheringId 멤버를 조회할 모임의 고유 ID
 * @return 모임에 속한 멤버 정보 리스트
 */
List<GetGatheringMemberListResult> findMembers(UUID gatheringId);

}
