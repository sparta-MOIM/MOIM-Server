package com.sparta.moim.member.domain.repository;

import com.sparta.moim.member.domain.Member;

public interface MemberRepository {
  Member save(Member member);
}
