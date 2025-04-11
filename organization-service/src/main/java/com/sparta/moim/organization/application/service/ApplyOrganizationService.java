package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.command.ApplyOrganizationCommand;
import com.sparta.moim.organization.application.exception.AlreadyOrganizationMember;
import com.sparta.moim.organization.application.usecase.ApplyOrganizationUseCase;
import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.repository.OrganizationApplicationRepository;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyOrganizationService implements ApplyOrganizationUseCase {

    private final OrganizationApplicationRepository organizationApplicationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    @Override
    @Transactional
    public void execute(String organizationTrackingId, String userTrackingId,
                        ApplyOrganizationCommand applyOrganizationCommand) {

        OrganizationMember organizationMember = organizationMemberRepository.findByUserTrackingIdAndOrganizationTrackingId(userTrackingId, organizationTrackingId).orElse(null);
        if(organizationMember != null) {
            throw new AlreadyOrganizationMember();
        }

        OrganizationApplication application = OrganizationApplication.from(
                organizationTrackingId,
                userTrackingId,
                applyOrganizationCommand);
        organizationApplicationRepository.save(application);
    }
}
