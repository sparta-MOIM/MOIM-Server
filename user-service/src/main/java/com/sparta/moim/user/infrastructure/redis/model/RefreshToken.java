package com.sparta.moim.user.infrastructure.redis.model;

import com.sparta.moim.user.constants.JwtConstants.Expiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "refresh_token", timeToLive = Expiry.REFRESH_TOKEN)
public class RefreshToken {

  @Id
  private String id;

}
