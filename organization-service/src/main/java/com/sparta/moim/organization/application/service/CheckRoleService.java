package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.exception.CannotFindOrganizationMember;
import com.sparta.moim.organization.application.exception.OrganizationMasterRequiredException;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.infrastruct.dto.OrganizationMemberCache;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CheckRoleService {

    private final OrganizationMemberRepository organizationMemberRepository;
    private final OrganizationCacheService organizationCacheService;

    public void checkMaster(String userTrackingId, String organizationTrackingId) {
        OrganizationMember member = organizationMemberRepository
                .findByUserTrackingIdAndOrganizationTrackingId(userTrackingId, organizationTrackingId)
                .orElseThrow(CannotFindOrganizationMember::new);

        if (!OrganizationMemberRole.MASTER.equals(member.getRole())) {
            throw new OrganizationMasterRequiredException();
        }
    }

//    @Transactional(readOnly = true)
    public boolean checkRole(String userTrackingId, String organizationTrackingId, List<OrganizationMemberRole> roles){

        // 캐시에서 멤버 정보 조회 시도
        Optional<OrganizationMemberCache> cachedMember = organizationCacheService.getMemberInfo(organizationTrackingId, userTrackingId);
        if(cachedMember.isPresent()){
            if(roles!=null && roles.contains(cachedMember.get().getRole())){
                return true;
            }
            return false;
        }


        // 캐시에 없으면, DB에서 멤버 정보 조회
        OrganizationMember member = organizationMemberRepository
                .findByUserTrackingIdAndOrganizationTrackingId(userTrackingId, organizationTrackingId).orElse(null);

        if(member == null){
            return false;
        }

        // 캐시 업데이트
        organizationCacheService.cacheMemberInfo(OrganizationMemberCache.from(member));

        if(roles!=null && roles.contains(member.getRole())){
            return true;
        }

        return false;
    }

}