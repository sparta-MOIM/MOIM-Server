package com.sparta.moim.session.member.infrastructure.repository;

import com.sparta.moim.session.member.domain.entity.Member;
import com.sparta.moim.session.member.domain.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
}
