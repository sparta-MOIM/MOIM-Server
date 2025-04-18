package com.sparta.moim.organization.domain.entity;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.organization.application.dto.command.CreateOrganizationCommand;
import com.sparta.moim.organization.application.dto.command.UpdateOrganizationCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Types;
import java.util.List;
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
@Builder // todo - private로 변경. 목데이터 넣어줄때 편하게 하려고 일단 지움.
@Table(name = "p_organization")
public class Organization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
    private UUID trackingId;

    @Column(nullable = false)
    private String organizationName;

    @Column
    private String description;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<OrganizationMember> organizationOrganizationMembers;

    public void updateFrom(UpdateOrganizationCommand updateOrganizationCommand) {
        this.organizationName = updateOrganizationCommand.getUpdateOrganizationName();
        this.description = updateOrganizationCommand.getUpdateDescription();
    }

    public static Organization from(CreateOrganizationCommand command){
        return Organization.builder()
                .organizationName(command.getOrganizationName())
                .description(command.getDescription())
                .build();
    }
}
