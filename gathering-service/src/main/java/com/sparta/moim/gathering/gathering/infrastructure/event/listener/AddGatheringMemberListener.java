package com.sparta.moim.gathering.gathering.infrastructure.event.listener;

import com.sparta.moim.gathering.gathering.application.dto.event.GatheringAddAdminEvent;
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
public class AddGatheringMemberListener {
  private final MemberRepository memberRepository;
  @EventListener
  @Transactional
  public void save(GatheringAddAdminEvent gatheringAddAdminEvent) {
    memberRepository.save(Member.from(gatheringAddAdminEvent));
  }
}
