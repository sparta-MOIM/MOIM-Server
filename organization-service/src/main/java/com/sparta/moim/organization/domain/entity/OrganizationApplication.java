package com.sparta.moim.organization.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.organization.application.dto.command.ApplyOrganizationCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "p_organization_application")
public class OrganizationApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
    private UUID trackingId;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name="user_tracking_id",length = 36, nullable = false)
    private UUID userTrackingId;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name="organization_tracking_id",length = 36, nullable = false)
    private UUID organizationTrackingId;

    @Column(nullable = false)
    private String content;

    public static OrganizationApplication from(String organizationTrackingId, String userTrackingId, ApplyOrganizationCommand applyOrganizationCommand) {
        return OrganizationApplication.builder()
                .userTrackingId(UUID.fromString(userTrackingId))
                .organizationTrackingId(UUID.fromString(organizationTrackingId))
                .content(applyOrganizationCommand.getContent())
                .build();
    }
}
