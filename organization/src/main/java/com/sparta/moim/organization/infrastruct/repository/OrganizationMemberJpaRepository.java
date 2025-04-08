package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.OrganizationMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationMemberJpaRepository extends JpaRepository<OrganizationMember, Long> {
}
