package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.exception.CannotFindOrganizationApplication;
import com.sparta.moim.organization.application.usecase.RejectOrganizationApplicationUseCase;
import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationApplicationRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RejectOrganizationApplicationService implements RejectOrganizationApplicationUseCase {

    private final OrganizationApplicationRepository organizationApplicationRepository;
    private final CheckRoleService checkRoleService;

    @Override
    @Transactional
    public void execute(String organizationTrackingId, String applicationTrackingId, String managerTrackingId) {

        checkRoleService.checkRole(managerTrackingId, organizationTrackingId, List.of(OrganizationMemberRole.MASTER, OrganizationMemberRole.MANAGER));
        OrganizationApplication organizationApplication = organizationApplicationRepository.findByApplicationTrackingIdAndOrganizationTrackingId(applicationTrackingId, organizationTrackingId)
                .orElseThrow(CannotFindOrganizationApplication::new);

        organizationApplication.softDelete(managerTrackingId); //todo -알림 발송과 나중에 연결시켜야함.
        organizationApplicationRepository.save(organizationApplication);
    }
}
