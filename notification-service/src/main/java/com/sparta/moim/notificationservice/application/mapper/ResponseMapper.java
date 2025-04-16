package com.sparta.moim.notificationservice.application.mapper;

import com.sparta.moim.notificationservice.application.dto.query.GetNotificationQuery;
import com.sparta.moim.notificationservice.presentation.dto.GetNotificationResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ResponseMapper {

    GetNotificationResponse toResponse(GetNotificationQuery getNotificationQuery);
}
