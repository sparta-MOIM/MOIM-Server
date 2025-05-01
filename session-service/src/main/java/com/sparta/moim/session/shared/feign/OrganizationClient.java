package com.sparta.moim.session.shared.feign;

import com.sparta.moim.common.response.ApiResponseData;
import com.sparta.moim.session.shared.enums.OrganizationMemberRole;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "organization-service", url = "https://moim.agong.store")
public interface OrganizationClient extends OrganizationService {

  @GetMapping("/internal/v1/organizations/{organizationTrackingId}/members/{userTrackingId}/has-role")
  ApiResponseData<Boolean> checkRole(
      @PathVariable(name = "organizationTrackingId") UUID organizationTrackingId,
      @PathVariable(name = "userTrackingId") UUID userTrackingId,
      @RequestParam(name = "roles") List<OrganizationMemberRole> roles);
}
