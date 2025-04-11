package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.command.UpdateOrganizationCommand;
import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.usecase.UpdateOrganizationUseCase;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrganizationService implements UpdateOrganizationUseCase {

    private final OrganizationRepository organizationRepository;
    private final CheckRoleService checkRoleService;

    @Override
    @Transactional
    public void execute(String organizationTrackingId, String userTrackingId, UpdateOrganizationCommand updateOrganizationCommand) {
        Organization organization = organizationRepository.findByTrackingId(organizationTrackingId).orElseThrow(
                CannotFindOrganization::new);
        checkRoleService.checkMaster(userTrackingId, organizationTrackingId);
        organization.updateFrom(updateOrganizationCommand);
        organizationRepository.save(organization);
    }
}
