package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.query.GetOrganizationQuery;
import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.mapper.ResponseMapper;
import com.sparta.moim.organization.application.usecase.GetOrganizationUseCase;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrganizationService implements GetOrganizationUseCase {

    private final OrganizationRepository organizationRepository;
    private final ResponseMapper responseMapper;

    @Override
    public GetOrganizationResponse execute(String organizationUuid) {
        Organization organization = organizationRepository.findByTrackingId(UUID.fromString(organizationUuid)).orElseThrow(CannotFindOrganization::new);
        return responseMapper.toResponse(GetOrganizationQuery.from(organization));
    }
}
