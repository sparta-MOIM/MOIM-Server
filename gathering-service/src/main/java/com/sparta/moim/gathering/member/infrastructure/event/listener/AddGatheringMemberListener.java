package com.sparta.moim.gathering.member.infrastructure.event.listener;

import com.sparta.moim.gathering.member.domain.Member;
import com.sparta.moim.gathering.member.domain.repository.MemberRepository;
import com.sparta.moim.gathering.shared.dto.SharedGatheringMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddGatheringMemberListener {
  private final MemberRepository memberRepository;
  @EventListener
  @Transactional
  public void save(SharedGatheringMember sharedGatheringMember) {
    try {
      log.info("소모임 멤버 저장 시작: {}", sharedGatheringMember.memberName());
      memberRepository.save(Member.from(sharedGatheringMember));
      log.info("소모임 멤버 저장 완료: {}", sharedGatheringMember.memberName());
    } catch (DataIntegrityViolationException e) {
      log.warn("멤버 저장 중복 오류: {}: {}", sharedGatheringMember.memberName(), e.getMessage());
        // 중복 데이터인 경우 별도 처리 로직
    }  catch (RuntimeException e) {
      log.error("소모임 멤버 저장 실패: {}: {}", sharedGatheringMember.memberName(), e.getMessage(), e);
      //TODO 보상 트랜잭션 적용
    }
  }
}
