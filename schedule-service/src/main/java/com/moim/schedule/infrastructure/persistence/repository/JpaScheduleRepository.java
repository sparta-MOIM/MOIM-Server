package com.moim.schedule.infrastructure.persistence.repository;

import com.moim.schedule.domain.Schedule;
import com.moim.schedule.domain.repository.command.ScheduleCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleCommandRepository {
}

