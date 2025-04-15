package com.sparta.moim.gathering.member.application.service;

import com.sparta.moim.gathering.member.application.dto.command.JoinGatheringCommand;
import com.sparta.moim.gathering.member.application.dto.command.LeaveGatheringCommand;
import com.sparta.moim.gathering.member.application.dto.command.RemoveGatheringCommand;
import com.sparta.moim.gathering.member.application.event.feign.InternalGatheringService;
import com.sparta.moim.gathering.member.domain.enums.MemberType;
import com.sparta.moim.gathering.member.domain.repository.MemberRepository;
import com.sparta.moim.gathering.shared.error.code.GatheringCode;
import com.sparta.moim.gathering.shared.error.exception.GatheringException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final InternalGatheringService internalGatheringService;

  public void joinGathering(JoinGatheringCommand command) {
    validateGatheringExists(command.gatheringId());
    statusTrueValidate(command);

    if(memberRepository.existsByMemberId(command.username())) {
      throw new GatheringException(GatheringCode.ALREADY_PARTICIPATE_GATHERING);
    }

    memberRepository.save(command.toDomain());
  }

  private void statusTrueValidate(JoinGatheringCommand command) {
    internalGatheringService.validateGatheringStatusOpen(command.gatheringId());
  }


  @Transactional
  public void leaveGathering(LeaveGatheringCommand command) {
    validateGatheringExists(command.gatheringId());
    memberRepository.deleteByGatheringIdAndMemberId(command.gatheringId(), command.username());
  }

  @Transactional
  public void removeGathering(RemoveGatheringCommand command) {
    MemberType memberType = memberRepository.findMemberType(command.gatheringId(), command.memberId());

    if(memberType == MemberType.GENERAL) {
      throw new GatheringException(GatheringCode.NOT_ALLOW_ROLE_GATHERING);
    }

    validateGatheringExists(command.gatheringId());
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId(), command.users());
  }

  private void validateGatheringExists(UUID id) {
    internalGatheringService.validateGatheringExists(id);
  }
}
