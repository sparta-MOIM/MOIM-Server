package com.sparta.moim.gathering.gathering.infrastructure.event.listener;

import com.sparta.moim.gathering.gathering.application.dto.event.GatheringRevokeAdminEvent;
import com.sparta.moim.gathering.gathering.application.exception.NotFoundGatheringMemberException;
import com.sparta.moim.gathering.gathering.domain.entity.Member;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RevokeGatheringMemberListener {
  private final MemberRepository memberRepository;

  @EventListener
  @Transactional
  public void revoke(GatheringRevokeAdminEvent revokeMember) {
    Member member = memberRepository.findByMemberStatusOwner(revokeMember.gatheringId(), revokeMember.ownerId())
        .orElseThrow(NotFoundGatheringMemberException::new);
    member.changeOwner(revokeMember.ownerId());
    log.info("소모임 관리자 수정 완료: {}", revokeMember.ownerId());
  }

}
