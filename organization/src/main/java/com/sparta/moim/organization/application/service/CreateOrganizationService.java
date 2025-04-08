package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.command.CreateOrganizationCommand;
import com.sparta.moim.organization.application.usecase.CreateOrganizationUseCase;
import com.sparta.moim.organization.domain.Organization;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrganizationService implements CreateOrganizationUseCase {

    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public void execute(CreateOrganizationCommand command) {
        organizationRepository.save(Organization.from(command));
    }
}
