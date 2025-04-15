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
    isExitsValidate(command.gatheringId());
    statusTrueValidate(command);
    memberRepository.save(command.toDomain());
  }

  private void statusTrueValidate(JoinGatheringCommand command) {
    gatheringService.isGatheringStatusOpen(command.gatheringId());
  }


  @Transactional
  public void leaveGathering(LeaveGatheringCommand command) {
    isExitsValidate(command.gatheringId());
    memberRepository.deleteByGatheringIdAndMemberId(command.gatheringId(), command.username());
  }

  @Transactional
  public void removeGathering(RemoveGatheringCommand command) {
    isExitsValidate(command.gatheringId());
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId(), command.users());
  }

  private void isExitsValidate(UUID id) {
    gatheringService.isExitsGathering(id);
  }
}
