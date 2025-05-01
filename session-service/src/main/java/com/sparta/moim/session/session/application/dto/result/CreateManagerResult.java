package com.sparta.moim.session.session.application.dto.result;

import com.sparta.moim.session.shared.enums.SessionStatus;

public record CreateManagerResult(String reason, SessionStatus status) {
}
