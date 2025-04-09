package com.sparta.moim.organization.application.service;

import com.sparta.moim.common.page.Pagination;
import com.sparta.moim.organization.application.dto.query.GetOrganizationQuery;
import com.sparta.moim.organization.application.dto.query.GetOrganizationSummaryQuery;
import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.mapper.ResponseMapper;
import com.sparta.moim.organization.application.usecase.GetOrganizationUseCase;
import com.sparta.moim.organization.application.util.PaginationMap;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;
import com.sparta.moim.organization.presentation.dto.GetOrganizationSummaryResponse;
import java.util.List;
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
        Organization organization = organizationRepository.findByTrackingId(organizationUuid).orElseThrow(CannotFindOrganization::new);
        return responseMapper.toResponse(GetOrganizationQuery.from(organization));
    }

    @Override
    public Pagination<GetOrganizationSummaryResponse> execute(int page, int size) {
        Pagination<Organization> organizations = organizationRepository.findAll(page, size);
        Pagination<GetOrganizationSummaryQuery> organizationSummaryQueries = PaginationMap.map(organizations, GetOrganizationSummaryQuery::from);
        List<GetOrganizationSummaryResponse> responseList = responseMapper.toResponse(organizationSummaryQueries.getContent());
        return Pagination.of(
                organizationSummaryQueries.getPage(),
                organizationSummaryQueries.getSize(),
                organizationSummaryQueries.getTotal(),
                responseList);
    }

}
