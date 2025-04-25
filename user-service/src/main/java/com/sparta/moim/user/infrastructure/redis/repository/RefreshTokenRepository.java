package com.sparta.moim.user.infrastructure.redis.repository;

import com.sparta.moim.user.infrastructure.redis.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
