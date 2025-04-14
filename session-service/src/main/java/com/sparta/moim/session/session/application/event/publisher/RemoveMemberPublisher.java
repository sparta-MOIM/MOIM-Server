package com.sparta.moim.session.session.application.event.publisher;

import com.sparta.moim.session.shared.dto.SharedRemoveSession;

public interface RemoveMemberPublisher {
  void remove(SharedRemoveSession removeSession);
}
