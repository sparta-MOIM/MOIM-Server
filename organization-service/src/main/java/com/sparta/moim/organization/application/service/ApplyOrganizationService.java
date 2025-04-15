package com.sparta.moim.organization.application.service;

import com.sparta.moim.organization.application.dto.command.ApplyOrganizationCommand;
import com.sparta.moim.organization.application.dto.message.ApplyOrganizationNotificationMessage;
import com.sparta.moim.organization.application.enums.NotificationType;
import com.sparta.moim.organization.application.exception.AlreadyOrganizationMember;
import com.sparta.moim.organization.application.exception.CannotFindOrganization;
import com.sparta.moim.organization.application.usecase.ApplyOrganizationUseCase;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationApplication;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.repository.OrganizationApplicationRepository;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import com.sparta.moim.organization.infrastruct.adaptor.out.ApplyOrganizationNotificationProducer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyOrganizationService implements ApplyOrganizationUseCase {

    private final OrganizationApplicationRepository organizationApplicationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final ApplyOrganizationNotificationProducer applyOrganizationNotificationProducer;
    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberService organizationMemberService;

    @Override
    @Transactional
    public void execute(String organizationTrackingId, String userTrackingId,
                        ApplyOrganizationCommand applyOrganizationCommand) {

        Organization organization = organizationRepository.findByTrackingId(organizationTrackingId).orElseThrow(
                CannotFindOrganization::new);

        OrganizationMember organizationMember = organizationMemberRepository.findByUserTrackingIdAndOrganizationTrackingId(userTrackingId, organizationTrackingId).orElse(null);
        if(organizationMember != null) {
            throw new AlreadyOrganizationMember();
        }

        OrganizationApplication application = OrganizationApplication.from(
                organizationTrackingId,
                userTrackingId,
                applyOrganizationCommand);
        organizationApplicationRepository.save(application);

        List<String> organizationMembersTrackingIds = organizationMemberService.getOrganizationManagers(organization)
                .stream()
                .map(OrganizationMember::getUserTrackingId)
                .map(String::valueOf)
                .toList();

        log.info("받는 사람 정보 : " + organizationMembersTrackingIds.get(0).toString());


        applyOrganizationNotificationProducer.send(
                ApplyOrganizationNotificationMessage.of(
                        NotificationType.ORGANIZATION_MOIM_REQUEST,
                        organizationTrackingId,
                        organization.getOrganizationName(),
                        userTrackingId,
                        "테스터", //todo - username을 헤더에서 꺼내서 보내줌.
                        organizationMembersTrackingIds
                )
        );

    }
}
