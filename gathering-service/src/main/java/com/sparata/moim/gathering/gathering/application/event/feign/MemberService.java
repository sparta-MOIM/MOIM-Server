package com.sparata.moim.gathering.gathering.application.event.feign;

import com.sparata.moim.gathering.gathering.application.dto.result.GetGatheringMemberListResult;
import java.util.List;
import java.util.UUID;

public interface MemberService {
  List<GetGatheringMemberListResult> findMembers(UUID gatheringId);

}
