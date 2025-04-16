package com.sparta.moim.gathering.member.infrastructure.event.listener;

import com.sparta.moim.gathering.member.domain.Member;
import com.sparta.moim.gathering.member.domain.repository.MemberRepository;
import com.sparta.moim.gathering.shared.dto.SharedGatheringRevokeMember;
import com.sparta.moim.gathering.shared.error.code.GatheringCode;
import com.sparta.moim.gathering.shared.error.exception.GatheringException;
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
  public void revoke(SharedGatheringRevokeMember revokeMember) {
    try {
      log.info("소모임 관리자 저장 시작: {}", revokeMember.ownerName());

      Member member = memberRepository.findByMemberStatusOwner(revokeMember.gatheringId(), revokeMember.ownerName())
          .orElseThrow(() -> new GatheringException(GatheringCode.NOT_FOUND_GATHERING_MEMBER));
      member.changeOwner(revokeMember.ownerName());
      log.info("소모임 관리자 수정 완료: {}", revokeMember.ownerName());
    } catch (RuntimeException e) {
      log.error("소모임 관리자 권한 변경 실패: {}: {}", revokeMember.ownerName(), e.getMessage(), e);
      //TODO 보상 트랜잭션 적용
    }
  }

}
