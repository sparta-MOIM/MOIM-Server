package com.sparta.moim.organization.application.mapper;

import com.sparta.moim.organization.application.dto.query.GetOrganizationQuery;
import com.sparta.moim.organization.application.dto.query.GetOrganizationSummaryQuery;
import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;
import com.sparta.moim.organization.presentation.dto.GetOrganizationSummaryResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ResponseMapper {

    GetOrganizationResponse toResponse(GetOrganizationQuery query);
    GetOrganizationSummaryResponse toResponse(GetOrganizationSummaryQuery query);
    List<GetOrganizationSummaryResponse> toResponse(List<GetOrganizationSummaryQuery> queries);
}
