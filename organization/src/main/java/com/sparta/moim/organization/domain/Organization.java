package com.sparta.moim.organization.domain;

import com.sparta.moim.common.utils.BaseEntity;
import com.sparta.moim.organization.application.dto.command.CreateOrganizationCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "p_organization")
public class Organization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private UUID uuid;

    @Column(nullable = false)
    private String organizationName;

    @Column
    private String description;

    @OneToMany(mappedBy = "organization")
    private List<Member> organizationMembers;

    public static Organization from(CreateOrganizationCommand command){
        return Organization.builder()
                .organizationName(command.getOrganizationName())
                .description(command.getDescription())
                .build();
    }
}
