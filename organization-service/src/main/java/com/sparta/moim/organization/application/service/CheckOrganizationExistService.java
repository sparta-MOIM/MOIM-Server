package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.usecase.CheckOrganizationExistsUseCase;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckOrganizationExistService implements CheckOrganizationExistsUseCase {

    private final OrganizationRepository organizationRepository;

    @Override
    public boolean execute(String organizationTrackingId) {
        return organizationRepository.existsByTrackingId(organizationTrackingId);
    }
}
