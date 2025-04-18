package com.sparta.moim.organization.infrastruct.util;

import com.github.javafaker.Faker;
import com.sparta.moim.organization.domain.entity.Organization;
import com.sparta.moim.organization.domain.entity.OrganizationMember;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataCreateService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private static final Faker faker = new Faker();

    @Transactional
    public void generateOrganizationBatch(int batchNumber, int batchSize, int organizationTotalCount) {
        List<Organization> organizations = new ArrayList<>();
        List<OrganizationMember> members = new ArrayList<>();

        int orgStartIndex = batchNumber * batchSize;
        for (int i = 0; i < batchSize; i++) {
            int orgIndex = orgStartIndex + i;
            Organization organization = Organization.builder()
                    .trackingId(UUID.randomUUID())
                    .organizationName("모임_" + orgIndex)
                    .description(faker.lorem().sentence())
                    .build();
            organizations.add(organization);
        }

        organizationRepository.saveAll(organizations);

        // 멤버 생성
        int totalMembers = 0;
        for (int i = 0; i < batchSize; i++) {
            Organization org = organizations.get(i);
            int memberCount = getMemberCountForOrganization(orgStartIndex + i, organizationTotalCount);

            // 역할 배분
            int managerCount = (int) (memberCount * 0.2);
            int memberCountOnly = memberCount - managerCount - 1; // 나머지 MEMBER, MASTER 1명
            Set<String> usedUserIds = new HashSet<>();
            for (int j = 0; j < memberCount; j++) {
                long userIndex = ((long)(orgStartIndex + i)) * 10_000 + j;
                String paddedId = String.format("%06d", userIndex % 100_000);
                // 중복 방지 로직
                while (!usedUserIds.add(paddedId)) {
                    userIndex++;
                    paddedId = String.format("%06d", userIndex % 100_000);
                }
                UUID userTrackingId = UUID.nameUUIDFromBytes(("user-" + paddedId).getBytes());

                OrganizationMemberRole role;
                if (j == 0) {
                    role = OrganizationMemberRole.MASTER; // 1명만
                } else if (j <= managerCount) {
                    role = OrganizationMemberRole.MANAGER; // 20% 관리자
                } else {
                    role = OrganizationMemberRole.MEMBER; // 나머지 80% 일반 멤버
                }

                OrganizationMember member = OrganizationMember.builder()
                        .trackingId(UUID.randomUUID())
                        .userTrackingId(userTrackingId)
                        .nickname(faker.name().username())
                        .role(role)
                        .organization(org)
                        .build();
                members.add(member);
            }

            totalMembers += memberCount;
        }

        organizationMemberRepository.saveAll(members);

        log.info("✅ 조직 batch[{}] - 조직 {}개 / 멤버 {}명 생성 완료", batchNumber, batchSize, totalMembers);
    }

    private int getMemberCountForOrganization(int orgIndex, int totalCount) {
        int quarter = totalCount / 4;

        if (orgIndex < quarter) return 10_000;
        if (orgIndex < quarter * 2) return 5_000;
        if (orgIndex < quarter * 3) return 1_000;
        return 500;
    }
}
