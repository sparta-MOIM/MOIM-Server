package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.command.ChangeOrganizationRoleCommand;
import com.sparta.moim.organization.application.exception.CannotChangeMasterRoleException;
import com.sparta.moim.organization.application.exception.CannotFindOrganizationMember;
import com.sparta.moim.organization.application.exception.MemberAccessDeniedException;
import com.sparta.moim.organization.application.usecase.ChangeAuthorityUseCase;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeAuthorityService implements ChangeAuthorityUseCase {

    private final OrganizationMemberRepository organizationMemberRepository;
    private final CheckRoleService checkRoleService;

    @Override
    @Transactional
    public void execute(String organizationTrackingId, String targetMemberTrackingId, String userTrackingId,
                        ChangeOrganizationRoleCommand command) {

        boolean checkRole = checkRoleService.checkRole(userTrackingId, organizationTrackingId, List.of(OrganizationMemberRole.MASTER));
        if(!checkRole){
            throw new MemberAccessDeniedException();
        }

        OrganizationMember targetMember = organizationMemberRepository.findByMemberTrackingId(targetMemberTrackingId).orElseThrow(
                CannotFindOrganizationMember::new);

        if(targetMember.getRole().equals(command.getRole())){
            return;
        }

        // MASTER는 별도의 MASTER 변경으로만 변경 가능
        if(targetMember.getRole().equals(OrganizationMemberRole.MASTER) || command.getRole().equals(OrganizationMemberRole.MASTER)){
            throw new CannotChangeMasterRoleException();
        }

        targetMember.updateRole(command.getRole());
        organizationMemberRepository.save(targetMember);
    }
}
