package com.sparta.moim.organization.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.organization.application.dto.query.GetOrganizationMemberQuery;
import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.mapper.ResponseMapper;
import com.sparta.moim.organization.application.usecase.GetOrganizationMemberListUseCase;
import com.sparta.moim.organization.application.util.PaginationMap;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import com.sparta.moim.organization.presentation.dto.GetOrganizationMemberResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOarganizationMemberListService implements GetOrganizationMemberListUseCase {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final ResponseMapper responseMapper;

    @Override
    public Pagination<GetOrganizationMemberResponse> execute(String organizationTrackingId, int page, int size) {
        Organization organization = organizationRepository.findByTrackingId(organizationTrackingId)
                .orElseThrow(CannotFindOrganization::new);

        Pagination<OrganizationMember> organizationMemberPagination = organizationMemberRepository.findAllByOrganization(
                organization, page, size);
        Pagination<GetOrganizationMemberQuery> organizationMemberQueryPagination = PaginationMap.map(organizationMemberPagination, GetOrganizationMemberQuery::from);
        return PaginationMap.map(organizationMemberQueryPagination, responseMapper::toResponse);
    }
}
