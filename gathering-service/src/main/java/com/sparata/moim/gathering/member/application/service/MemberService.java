package com.sparata.moim.gathering.member.application.service;

import com.sparata.moim.gathering.member.application.dto.command.JoinGatheringCommand;
import com.sparata.moim.gathering.member.application.dto.command.LeaveGatheringCommand;
import com.sparata.moim.gathering.member.application.dto.command.RemoveGatheringCommand;
import com.sparata.moim.gathering.member.application.event.feign.GatheringService;
import com.sparata.moim.gathering.member.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final GatheringService gatheringService;

  public void joinGathering(JoinGatheringCommand command) {
    validate(command.gatheringId());
    memberRepository.save(command.toDomain());
  }


  @Transactional
  public void leaveGathering(LeaveGatheringCommand command) {
    validate(command.gatheringId());
    memberRepository.deleteByGatheringIdAndMemberId(command.gatheringId().toString(), command.username());
  }

  @Transactional
  public void removeGathering(RemoveGatheringCommand command) {
    validate(command.gatheringId());
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId().toString(), command.users());
  }

  private void validate(UUID id) {
    gatheringService.isExitsGathering(id);
  }
}
