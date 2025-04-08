package com.sparta.moim.organization.application.mapper;

import com.sparta.moim.organization.application.dto.query.GetOrganizationQuery;
import com.sparta.moim.organization.presentation.dto.GetOrganizationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ResponseMapper {

    public GetOrganizationResponse toResponse(GetOrganizationQuery query);
}
