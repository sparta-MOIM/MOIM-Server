package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.command.UpdateMemberInfoCommand;
import com.sparta.moim.organization.application.exception.AlreadyUsedNickname;
import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.exception.CannotFindOrganizationMember;
import com.sparta.moim.organization.application.usecase.UpdateMemberInfoUseCase;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberInfoService implements UpdateMemberInfoUseCase {
    private final OrganizationMemberRepository organizationMemberRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    public void execute(String organizationTrackingId, String userTrackingId, UpdateMemberInfoCommand command) {

        OrganizationMember organizationMember = organizationMemberRepository.findByUserTrackingIdAndOrganizationTrackingId(
                userTrackingId, organizationTrackingId).orElseThrow(CannotFindOrganizationMember::new);

        Organization organization = organizationRepository.findByTrackingId(organizationTrackingId)
                .orElseThrow(CannotFindOrganization::new);

        OrganizationMember byOrganizationAndNickname = organizationMemberRepository.findByOrganizationAndNickname(
                organization, command.getNickname()).orElse(null);

        if (byOrganizationAndNickname != null && !byOrganizationAndNickname.getUserTrackingId().equals(organizationMember.getTrackingId())) {
            throw new AlreadyUsedNickname();
        }

        organizationMember.updateInfo(command);
        organizationMemberRepository.save(organizationMember);
    }
}
