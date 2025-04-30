package com.sparta.moim.gathering.gathering.application.service;


import com.sparta.moim.gathering.gathering.application.dto.command.event.LeaveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.RemoveGatheringCommand;
import com.sparta.moim.gathering.gathering.application.dto.command.event.JoinGatheringCommand;

public interface MemberService {
  void joinGathering(JoinGatheringCommand command);
  void leaveGathering(LeaveGatheringCommand command);
  void removeGathering(RemoveGatheringCommand command);

}
