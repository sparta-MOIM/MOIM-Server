package com.sparta.moim.gathering.gathering.application.service.struct;

import com.sparta.moim.gathering.gathering.application.dto.command.event.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.exception.AlreadyParticipateFoundGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotFoundGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.NotFoundGatheringMemberException;
import com.sparta.moim.gathering.gathering.application.exception.NotOpenGatheringException;
import com.sparta.moim.gathering.gathering.application.exception.RoleNotAllowedGatheringException;
import com.sparta.moim.gathering.gathering.domain.repository.GatheringValidationRepository;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import com.sparta.moim.gathering.shared.enums.MemberType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceStruct {
  private final MemberRepository memberRepository;
  private final GatheringValidationRepository gatheringValidationRepository;

  public void joinGathering(JoinGatheringCommand command) {
    validateGatheringExists(command.gatheringId());
    statusTrueValidate(command);

    if (memberRepository.existsByGatheringIdAndMemberId(command.gatheringId(), command.username())) {
      throw new AlreadyParticipateFoundGatheringException();
    }

  }

  private void statusTrueValidate(JoinGatheringCommand command) {
    if (!gatheringValidationRepository.isGatheringOpen(command.gatheringId())) {
      throw new NotOpenGatheringException();
    }
  }

  public void leaveGathering(LeaveGatheringCommand command) {
    validateGatheringExists(command.gatheringId());

    UUID gatheringId = command.gatheringId();
    UUID username = command.username();

    if (!memberRepository.existsByGatheringIdAndMemberId(gatheringId, username)) {
      throw new NotFoundGatheringMemberException();
    }


  }

  @Transactional
  public void removeGathering(RemoveGatheringCommand command) {
    MemberType memberType = memberRepository.findMemberType(command.gatheringId(), command.memberId());

    if (memberType == MemberType.GENERAL) {
      throw new RoleNotAllowedGatheringException();
    }

    validateGatheringExists(command.gatheringId());
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId(), command.users());
  }

  private void validateGatheringExists(UUID id) {
    if (!gatheringValidationRepository.existsByTrackingIdAndDeletedAtNull(id)) {
      throw new NotFoundGatheringException();
    }
  }
}
