package com.sparta.moim.session.member.domain.repository;

import com.sparta.moim.session.member.domain.entity.Member;

public interface MemberRepository {
  Member save(Member member);
}
