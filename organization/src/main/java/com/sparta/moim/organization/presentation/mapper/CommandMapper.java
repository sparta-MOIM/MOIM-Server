package com.sparta.moim.organization.presentation.mapper;

import com.sparta.moim.organization.application.dto.command.CreateOrganizationCommand;
import com.sparta.moim.organization.presentation.dto.CreateOrganizationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CommandMapper {

    public CreateOrganizationCommand toCommand(CreateOrganizationRequest request);
}