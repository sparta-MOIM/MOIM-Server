package com.sparta.moim.gathering.gathering.application.event.feigin;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.gathering.shared.enums.OrganizationMemberRole;
import java.util.List;
import java.util.UUID;

public interface OrganizationService {

  ApiResponseData<Boolean> checkRole(UUID organizationTrackingId,
                                     UUID userTrackingId,
                                     List<OrganizationMemberRole> roles);
}
