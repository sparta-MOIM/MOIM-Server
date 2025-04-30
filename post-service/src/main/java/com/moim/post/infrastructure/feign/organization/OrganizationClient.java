package com.moim.post.infrastructure.feign.organization;

import com.sparta.moim.common.response.ApiResponseData;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "organization-service", url = "${feign.url.organization}")
public interface OrganizationClient {
  @GetMapping("/{organizationTrackingId}/members/{userTrackingId}/has-role")
  ApiResponseData<Boolean> checkRole(
      @PathVariable("organizationTrackingId") String organizationTrackingId,
      @PathVariable("userTrackingId") String userTrackingId,
      @RequestParam("roles") List<OrganizationMemberRole> roles
  );


}