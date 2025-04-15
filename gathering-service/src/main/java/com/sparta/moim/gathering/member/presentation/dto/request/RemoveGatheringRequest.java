package com.sparta.moim.gathering.member.presentation.dto.request;

import com.sparta.moim.gathering.member.application.dto.command.RemoveGatheringCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record RemoveGatheringRequest(
    @NotNull
    @Size(min = 1, message = "At least one user must be specified")
    List<@NotBlank String> users
) {
  /**
   * 요청 데이터를 사용하여 RemoveGatheringCommand 객체로 변환합니다.
   *
   * @param gatheringId 모임의 고유 식별자
   * @param memberId 삭제 요청을 수행하는 회원의 식별자
   * @return 모임에서 사용자를 제거하기 위한 RemoveGatheringCommand 객체
   */
  public RemoveGatheringCommand toCommand(UUID gatheringId, String memberId) {
    return new RemoveGatheringCommand(gatheringId, users, memberId);
  }
}
