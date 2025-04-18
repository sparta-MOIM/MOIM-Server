package com.sparta.moim.organization.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.organization.application.dto.command.UpdateMemberInfoCommand;
import com.sparta.moim.organization.domain.enums.OrganizationMemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.sql.Types;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "p_organization_member",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"organization_id", "user_tracking_id"})
    }
)
public class OrganizationMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @UuidGenerator
//    @JdbcTypeCode(Types.VARCHAR)
//    @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
//    private UUID trackingId;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name="user_tracking_id",length = 36, nullable = false)
    private UUID userTrackingId;

    @Column(length = 100)
    private String nickname;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private OrganizationMemberRole role;

    public static OrganizationMember of(UUID userTrackingId, String nickname, OrganizationMemberRole role, Organization organization) {
        return OrganizationMember.builder()
                .userTrackingId(userTrackingId)
                .nickname(nickname)
                .role(role)
                .organization(organization)
                .build();
    }

    public static OrganizationMember from(OrganizationApplication organizationApplication, Organization organization) {
        return OrganizationMember.builder()
                .userTrackingId(organizationApplication.getUserTrackingId())
                .nickname(null)
                .role(OrganizationMemberRole.MEMBER)
                .organization(organization)
                .build();
    }

    public void updateInfo(UpdateMemberInfoCommand command) {
        this.nickname = command.getNickname();
    }
}
