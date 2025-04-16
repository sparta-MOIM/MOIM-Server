package com.moim.post.application.validation;

import com.moim.post.infrastructure.feign.organization.OrganizationClient;
import com.moim.post.infrastructure.feign.organization.OrganizationMemberRole;
import com.sparta.moim.common.response.ApiResponseData;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleValidator {

  private static final Map<String, List<OrganizationMemberRole>> REQUEST_ROLE = Map.of(
      "READ", List.of(OrganizationMemberRole.MEMBER, OrganizationMemberRole.MANAGER, OrganizationMemberRole.MASTER),
      "CREATE", List.of(OrganizationMemberRole.MANAGER, OrganizationMemberRole.MASTER),
      "UPDATE", List.of(OrganizationMemberRole.MANAGER, OrganizationMemberRole.MASTER),
      "DELETE", List.of(OrganizationMemberRole.MANAGER, OrganizationMemberRole.MASTER)
  );

  private final OrganizationClient organizationClient;

  public Boolean isValidRole(
      String requestType,
      UUID organizationTrackingId,
      UUID userTrackingId
  ) {
    ApiResponseData<Boolean> result = organizationClient.checkRole(
        organizationTrackingId.toString(),
        userTrackingId.toString(),
        REQUEST_ROLE.getOrDefault(requestType.toUpperCase(), List.of())
    );
    return result.getData();
  }
}
