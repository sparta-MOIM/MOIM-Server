package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.command.CreateOrganizationCommand;
import com.sparta.moim.organization.application.usecase.CreateOrganizationUseCase;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrganizationService implements CreateOrganizationUseCase {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    @Override
    @Transactional
    public void execute(String userTrackingID, CreateOrganizationCommand createOrganizationCommand) {

        Organization organization = organizationRepository.save(Organization.from(createOrganizationCommand));

        OrganizationMember organizationMember = OrganizationMember.of(
                UUID.fromString(userTrackingID), // todo-user의 트래킹Id로 변경해야함.
                createOrganizationCommand.getNickname(),
                OrganizationMemberRole.MASTER,
                organization);
        organizationMemberRepository.save(organizationMember);
    }
}
