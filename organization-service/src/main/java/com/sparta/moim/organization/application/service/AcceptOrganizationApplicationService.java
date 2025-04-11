package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.exception.CannotFindOrganizationApplication;
import com.sparta.moim.organization.application.exception.MemberAccessDeniedException;
import com.sparta.moim.organization.application.usecase.AcceptOrganizationApplicationUseCase;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationApplicationRepository;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AcceptOrganizationApplicationService implements AcceptOrganizationApplicationUseCase {

    private final OrganizationApplicationRepository organizationApplicationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final OrganizationRepository organizationRepository;
    private final CheckRoleService checkRoleService;

    @Override
    @Transactional
    public void execute(String organizationTrackingId, String applicationTrackingId, String managerTrackingId) {

        if(checkRoleService.checkRole(managerTrackingId, organizationTrackingId, List.of(OrganizationMemberRole.MASTER, OrganizationMemberRole.MANAGER))){
            throw new MemberAccessDeniedException();
        }

        OrganizationApplication organizationApplication = organizationApplicationRepository.findByApplicationTrackingIdAndOrganizationTrackingId(applicationTrackingId, organizationTrackingId)
                .orElseThrow(CannotFindOrganizationApplication::new);

        Organization organization = organizationRepository.findByTrackingId(organizationTrackingId).orElseThrow(
                CannotFindOrganization::new);

        OrganizationMember organizationMember = OrganizationMember.from(
                organizationApplication,
                organization);

        organizationMemberRepository.save(organizationMember);
        organizationApplication.softDelete(managerTrackingId);
        organizationApplicationRepository.save(organizationApplication);
    }
}
