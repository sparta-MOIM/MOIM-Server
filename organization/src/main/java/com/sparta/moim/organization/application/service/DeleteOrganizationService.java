package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.exception.CannotFindOrganizationMember;
import com.sparta.moim.organization.application.exception.OrganizationMasterRequiredException;
import com.sparta.moim.organization.application.usecase.DeleteOrganizationUseCase;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOrganizationService implements DeleteOrganizationUseCase {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    @Override
    public void execute(String userTrackingId, String organizationTrackingId) {

        Organization organization = organizationRepository.findByTrackingId(organizationTrackingId)
                .orElseThrow(CannotFindOrganization::new);
        checkMaster(userTrackingId, organizationTrackingId); //todo-추후에 서비스 admin도 모임 삭제 허용해주어야함.
        deleteOrganizationAndMembers(userTrackingId, organization);
    }

    private void deleteOrganizationAndMembers(String userTrackingId, Organization organization) {
        List<OrganizationMember> members = organizationMemberRepository.findAllByOrganization(organization);
        organization.softDelete(userTrackingId);

        members.forEach(member ->{
            member.softDelete(userTrackingId);
        });
        organizationRepository.save(organization);
        organizationMemberRepository.saveAll(members);
    }

    private void checkMaster(String userTrackingId, String organizationTrackingId) {
        OrganizationMember organizationMember = organizationMemberRepository.findByUserTrackingIdAndOrganizationTrackingId(
                        userTrackingId, organizationTrackingId)
                .orElseThrow(CannotFindOrganizationMember::new);
        if(!OrganizationMemberRole.MASTER.equals(organizationMember.getRole())){
            throw new OrganizationMasterRequiredException();
        }
    }
}
