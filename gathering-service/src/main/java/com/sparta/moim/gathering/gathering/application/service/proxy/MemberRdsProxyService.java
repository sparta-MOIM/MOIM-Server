package com.sparta.moim.gathering.gathering.application.service.proxy;

import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.service.MemberService;
import com.sparta.moim.gathering.gathering.application.service.struct.MemberServiceStruct;
import com.sparta.moim.gathering.gathering.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRdsProxyService implements MemberService {
  private final MemberServiceStruct memberServiceStruct;
  private final MemberRepository memberRepository;

  @Override
  public void joinGathering(JoinGatheringCommand command) {
    memberServiceStruct.joinGathering(command);
    memberRepository.save(command.toDomain());
  }

  @Override
  public void leaveGathering(LeaveGatheringCommand command) {
    memberServiceStruct.leaveGathering(command);
  }

  @Override
  public void removeGathering(RemoveGatheringCommand command) {
    memberServiceStruct.removeGathering(command);
  }
}
