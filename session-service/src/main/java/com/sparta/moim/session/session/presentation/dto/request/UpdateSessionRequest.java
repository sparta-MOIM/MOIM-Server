package com.sparta.moim.session.session.presentation.dto.request;

import com.sparta.moim.session.session.application.dto.command.UpdateSessionCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateSessionRequest(
    @Nullable String title,
    @PositiveOrZero int count
) {
  /**
   * 주어진 세션 ID를 이용하여 업데이트 명령 객체로 변환합니다.
   * 현재 요청의 title 및 count 값을 포함하여 새로운 UpdateSessionCommand 인스턴스를 생성합니다.
   *
   * @param sessionId 업데이트 대상 세션의 UUID
   * @return 업데이트 명령을 나타내는 UpdateSessionCommand 객체
   */
  public UpdateSessionCommand toCommand(UUID sessionId) {
    return UpdateSessionCommand.builder()
        .sessionId(sessionId)
        .title(title)
        .count(count).build();
  }
}
