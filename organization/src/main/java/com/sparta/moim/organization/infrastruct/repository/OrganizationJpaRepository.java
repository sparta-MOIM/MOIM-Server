package com.sparta.moim.organization.infrastruct.repository;

import com.sparta.moim.organization.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationJpaRepository extends JpaRepository<Organization, Long> {
}
