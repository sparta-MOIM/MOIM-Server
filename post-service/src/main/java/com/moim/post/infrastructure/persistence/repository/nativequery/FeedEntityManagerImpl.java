package com.moim.post.infrastructure.persistence.repository.nativequery;

import com.moim.post.domain.repository.command.entitymanager.FeedEntityManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FeedEntityManagerImpl implements FeedEntityManager {

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public void updateTaggedUserIds(Long feedId, List<UUID> userIds) {
    em.createNativeQuery("DELETE FROM feed_tagged_user_ids WHERE feed_id = :feedId")
        .setParameter("feedId", feedId)
        .executeUpdate();

    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("INSERT INTO feed_tagged_user_ids (feed_id, tagged_user_ids) VALUES ");

    for (int i = 0; i < userIds.size(); i++) {
      queryBuilder.append("(:feedId, :userId").append(i).append(")");
      if (i < userIds.size() - 1) {
        queryBuilder.append(", ");
      }
    }

    Query query = em.createNativeQuery(queryBuilder.toString())
        .setParameter("feedId", feedId);

    for (int i = 0; i < userIds.size(); i++) {
      query.setParameter("userId" + i, userIds.get(i).toString());
    }

    query.executeUpdate();
  }

}
