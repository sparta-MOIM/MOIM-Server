package com.sparta.moim.session.shared.dto;

import java.util.UUID;

public record SharedRemoveMember(UUID sessionId, long count)
{ }
