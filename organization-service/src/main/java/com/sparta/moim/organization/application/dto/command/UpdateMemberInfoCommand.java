package com.sparta.moim.organization.application.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberInfoCommand {

    private String nickname;
}
