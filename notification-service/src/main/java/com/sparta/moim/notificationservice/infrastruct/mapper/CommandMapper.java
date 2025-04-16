package com.sparta.moim.notificationservice.infrastruct.mapper;

import com.sparta.moim.notificationservice.application.dto.command.ApplyOrganizationNotificationCommand;
import com.sparta.moim.notificationservice.infrastruct.dto.message.ApplyOrganizationNotificationMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CommandMapper {

    ApplyOrganizationNotificationCommand toCommand(ApplyOrganizationNotificationMessage message);
}