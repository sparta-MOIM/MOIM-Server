package com.sparata.moim.gathering.member.infrastructure.event.listener;

import com.sparata.moim.gathering.gathering.domain.repository.GatheringRepository;
import com.sparata.moim.gathering.member.domain.Member;
import com.sparata.moim.gathering.member.domain.repository.MemberRepository;
import com.sparata.moim.gathering.shared.dto.SharedGatheringMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddGatheringMemberListener {
  private final MemberRepository memberRepository;
  @EventListener
  public void save(SharedGatheringMember sharedGatheringMember) {
    try {
      log.info("Saving publisher {}", sharedGatheringMember.memberName());
      memberRepository.save(Member.from(sharedGatheringMember));
      log.info("Successfully saved publisher {}", sharedGatheringMember.memberName());
    } catch (Exception e) {
      log.error("Failed to save publisher {}: {}", sharedGatheringMember.memberName(), e.getMessage(), e);
      //TODO 보상 트랜잭션 적용
    }
  }
}
