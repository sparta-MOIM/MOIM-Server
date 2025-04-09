package com.sparta.moim.organization.presentation.mapper;

import com.sparta.moim.organization.application.dto.command.ApplyOrganizationCommand;
import com.sparta.moim.organization.application.dto.command.CreateOrganizationCommand;
import com.sparta.moim.organization.application.dto.command.UpdateOrganizationCommand;
import com.sparta.moim.organization.presentation.dto.ApplyOrganizationRequest;
import com.sparta.moim.organization.presentation.dto.CreateOrganizationRequest;
import com.sparta.moim.organization.presentation.dto.UpdateOrganizationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CommandMapper {

    CreateOrganizationCommand toCommand(CreateOrganizationRequest request);
    UpdateOrganizationCommand toCommand(UpdateOrganizationRequest request);
    ApplyOrganizationCommand toCommand(ApplyOrganizationRequest request);
}