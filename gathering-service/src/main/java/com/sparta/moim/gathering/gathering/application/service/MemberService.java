package com.sparta.moim.gathering.gathering.application.service;

import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.JoinGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.SearchGatheringCommand.RemoveGatheringCommand;

public interface MemberService {
  void joinGathering(JoinGatheringCommand command);
  void leaveGathering(LeaveGatheringCommand command);
  void removeGathering(RemoveGatheringCommand command);

}
