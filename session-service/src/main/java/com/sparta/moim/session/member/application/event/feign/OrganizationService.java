package com.sparta.moim.session.member.application.event.feign;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.session.shared.enums.OrganizationMemberRole;
import java.util.List;
import java.util.UUID;

public interface OrganizationService {
  ApiResponseData<Boolean> checkRole(UUID organizationTrackingId, UUID userTrackingId,
                                     List<OrganizationMemberRole> roles);
}
