package com.sparta.moim.organization.application.usecase;

import com.sparta.moim.organization.application.dto.command.UpdateMemberInfoCommand;

public interface UpdateMemberInfoUseCase {

    void execute(String organizationTrackingId, String userTrackingId, UpdateMemberInfoCommand command);

}
