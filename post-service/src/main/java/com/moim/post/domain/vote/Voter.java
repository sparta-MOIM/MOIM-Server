package com.moim.post.domain.vote;

import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.UUID;

@Entity
@Getter
@Builder
@Table(name = "p_voter")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "tracking_id", length = 36, nullable = false, unique = true)
    private UUID trackingId;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "post_id", length = 36, nullable = false, unique = true)
    private UUID postId;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "user_id", length = 36, nullable = false, unique = true)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VoteStatus status;

}
