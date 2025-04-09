package com.sparta.moim.organization.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
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
import java.sql.Types;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "p_organization_member")
public class OrganizationMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
    private UUID trackingId;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name="user_tracking_id",length = 36, nullable = false, unique = true)
    private UUID userTrackingId;

    @Column(nullable = false, length = 50)
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

}
