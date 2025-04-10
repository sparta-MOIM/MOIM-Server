package com.sparta.moim.session.session.infrastructure.repository;

import com.sparta.moim.session.session.domain.entity.Session;
import com.sparta.moim.session.session.domain.repository.SessionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSessionRepository extends JpaRepository<Session, Long>, SessionRepository {
}
