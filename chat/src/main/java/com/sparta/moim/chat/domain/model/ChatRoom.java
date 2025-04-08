package com.sparta.moim.chat.domain.model;

import com.sparta.moim.chat.presentation.request.ChatRoomRequestDTO;
import com.sparta.moim.common.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Types;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Builder
@Getter
@Table(name="chat_room")
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  private String chatRoom;

  private Long organizationId;

  @UuidGenerator
  @JdbcTypeCode(Types.VARCHAR)
  @Column(length = 36, nullable = false, unique = true)
  private UUID trackingId;

  public static ChatRoom from(ChatRoomRequestDTO chatRoomRequestDTO){
    return ChatRoom.builder()
        .chatRoom(chatRoomRequestDTO.getChat_room())
        .organizationId(chatRoomRequestDTO.getOrganization_id())
        .build();
  }

}
