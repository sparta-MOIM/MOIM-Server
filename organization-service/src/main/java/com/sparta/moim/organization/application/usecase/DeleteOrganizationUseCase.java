package com.sparta.moim.organization.application.usecase;

public interface DeleteOrganizationUseCase {
    void execute(String userTrackingId, String organizationId);
}
