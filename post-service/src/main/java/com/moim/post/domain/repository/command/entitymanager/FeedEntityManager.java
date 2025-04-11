package com.moim.post.domain.repository.command.entitymanager;

import java.util.List;
import java.util.UUID;

public interface FeedEntityManager {
  void updateTaggedUserIds(Long feedId, List<UUID> userIds);
}
