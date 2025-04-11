package com.sparta.moim.organization.application.usecase;

public interface RejectOrganizationApplicationUseCase {
    void execute(String organizationTrackingId, String applicationTrackingId, String managerTrackingId);
}
