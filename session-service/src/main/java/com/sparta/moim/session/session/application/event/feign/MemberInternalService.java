package com.sparta.moim.session.session.application.event.feign;

import com.sparta.moim.session.session.application.dto.result.GetSessionMemberListResult;
import java.util.List;
import java.util.UUID;

public interface MemberInternalService {
  List<GetSessionMemberListResult> getMembers(UUID sessionId);
}
