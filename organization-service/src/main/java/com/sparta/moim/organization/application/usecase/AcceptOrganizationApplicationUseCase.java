package com.sparta.moim.organization.application.usecase;

public interface AcceptOrganizationApplicationUseCase {
    void execute(String organizationTrackingId, String applicationTrackingId, String managerTrackingId);
}
