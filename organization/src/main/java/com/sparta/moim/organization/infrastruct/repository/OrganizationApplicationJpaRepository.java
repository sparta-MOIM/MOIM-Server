package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationApplicationJpaRepository extends JpaRepository<OrganizationApplication, Long> {
}
