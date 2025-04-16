package com.sparta.moim.gathering.gathering.application.service;

import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.domain.enums.MemberType;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import com.sparta.moim.gathering.gathering.application.code.GatheringCode;
import com.sparta.moim.gathering.gathering.application.exception.GatheringException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final GatheringValidationRepository gatheringValidationRepository;

  public void joinGathering(JoinGatheringCommand command) {
    validateGatheringExists(command.gatheringId());
    statusTrueValidate(command);

    if(memberRepository.existsByMemberId(command.username())) {
      throw new GatheringException(GatheringCode.ALREADY_PARTICIPATE_GATHERING);
    }

    memberRepository.save(command.toDomain());
  }

  private void statusTrueValidate(JoinGatheringCommand command) {
    gatheringValidationRepository.isGatheringOpen(command.gatheringId());
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
      throw new GatheringException(GatheringCode.ROLE_NOT_ALLOWED_GATHERING);
    }

    validateGatheringExists(command.gatheringId());
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId(), command.users());
  }

  private void validateGatheringExists(UUID id) {
    gatheringValidationRepository.existsByTrackingIdAndDeletedAtNull(id);
  }
}
