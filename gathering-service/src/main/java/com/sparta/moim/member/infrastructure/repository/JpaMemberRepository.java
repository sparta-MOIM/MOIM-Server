package com.sparta.moim.member.infrastructure.repository;

import com.sparta.moim.member.domain.Member;
import com.sparta.moim.member.domain.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
}
