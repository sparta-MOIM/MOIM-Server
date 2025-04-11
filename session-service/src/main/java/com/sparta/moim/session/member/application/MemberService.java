package com.sparta.moim.session.member.application;

import com.sparta.moim.session.member.application.dto.command.GetMemberCommand;
import com.sparta.moim.session.member.application.dto.command.JoinMemberCommand;
import com.sparta.moim.session.member.application.dto.command.LeaveMemberCommand;
import com.sparta.moim.session.member.application.dto.command.RemoveMemberCommand;
import com.sparta.moim.session.member.application.dto.result.GetMemberListResult;
import com.sparta.moim.session.member.application.dto.result.GetMemberResult;
import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.enums.MemberType;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  @Transactional
  public void joinMember(JoinMemberCommand command) {
    // CLOSE가 된 세션은 참여가 불가능합니다.
    // 이미 참여한 계정은 재 참여가 불가능합니다.
    memberRepository.save(Member.builder()
        .sessionId(command.sessionId())
        .type(MemberType.GENERAL)
        .memberName(command.username())
        .build());
  }

  @Transactional
  public void leaveMember(LeaveMemberCommand command) {
    memberRepository.delete(Member.builder()
        .sessionId(command.sessionId())
        .memberName(command.username())
        .build());
  }

  @Transactional
  public void removeMember(RemoveMemberCommand command) {
    memberRepository.removeMembers(command.sessionId(), command.members());
  }

  @Transactional(readOnly = true)
  public GetMemberResult getMember(GetMemberCommand command) {
    return GetMemberResult.get(memberRepository.findAllBySessionId(command.sessionId()));
  }
}
