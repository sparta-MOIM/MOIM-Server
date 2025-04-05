package com.sparta.moim.common.utils;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @CreatedBy
    @Column(updatable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedDateTime;

    @LastModifiedBy
    @Column(length = 100)
    private String modifiedBy;

    private LocalDateTime deletedAt;

    @Column(length = 100)
    private String deletedBy;

    public void softDelete(String userName) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userName;
    }
}