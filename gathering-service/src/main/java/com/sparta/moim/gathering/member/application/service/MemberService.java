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

  /**
   * 사용자가 모임에 참여하도록 등록합니다.
   *
   * 모임의 존재 여부와 참여 가능 상태를 확인한 후, 이미 참여 중인 사용자인 경우 예외를 발생시킵니다.
   * 중복 참여가 아닌 경우, 사용자를 모임에 참여자로 저장합니다.
   *
   * @throws GatheringException 사용자가 이미 다른 모임에 참여 중인 경우 발생합니다.
   */
  public void joinGathering(JoinGatheringCommand command) {
    validateGatheringExists(command.gatheringId());
    statusTrueValidate(command);

    if(memberRepository.existsByMemberId(command.username())) {
      throw new GatheringException(GatheringCode.ALREADY_PARTICIPATE_GATHERING);
    }

    memberRepository.save(command.toDomain());
  }

  /**
   * 주어진 모임이 참가 신청이 가능한 상태인지 검증합니다.
   *
   * 모임이 열려 있지 않으면 예외가 발생할 수 있습니다.
   */
  private void statusTrueValidate(JoinGatheringCommand command) {
    internalGatheringService.validateGatheringStatusOpen(command.gatheringId());
  }


  /**
   * 모임에서 회원의 참여를 취소합니다.
   *
   * @param command 탈퇴할 모임과 회원 정보를 포함한 명령 객체
   */
  @Transactional
  public void leaveGathering(LeaveGatheringCommand command) {
    validateGatheringExists(command.gatheringId());
    memberRepository.deleteByGatheringIdAndMemberId(command.gatheringId(), command.username());
  }

  /**
   * 지정된 모임에서 여러 회원을 삭제합니다.
   *
   * 회원의 역할이 일반 회원(GENERAL)인 경우 삭제 권한이 없어 예외가 발생합니다.
   * 모임의 존재 여부를 검증한 후, 전달된 회원 목록을 모임에서 일괄 삭제합니다.
   *
   * @param command 삭제할 모임 ID, 요청자 회원 ID, 삭제 대상 회원 목록을 포함한 명령 객체
   * @throws GatheringException 요청자의 역할이 삭제 권한이 없을 경우 발생합니다.
   */
  @Transactional
  public void removeGathering(RemoveGatheringCommand command) {
    MemberType memberType = memberRepository.findMemberType(command.gatheringId(), command.memberId());

    if(memberType == MemberType.GENERAL) {
      throw new GatheringException(GatheringCode.ROLE_NOT_ALLOWED_GATHERING);
    }

    validateGatheringExists(command.gatheringId());
    memberRepository.deleteAllByGatheringIdAndMembers(command.gatheringId(), command.users());
  }

  /**
   * 주어진 ID의 모임이 존재하는지 검증합니다.
   *
   * @param id 모임의 고유 식별자
   */
  private void validateGatheringExists(UUID id) {
    internalGatheringService.validateGatheringExists(id);
  }
}
