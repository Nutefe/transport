package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.RefreshTokens;

import java.util.Optional;

public interface RefreshTokenDao {

    Optional<RefreshTokens> findByToken(String token);

    RefreshTokens createRefreshToken(Long userId);

    RefreshTokens verifyExpiration(RefreshTokens token);

    int deleteByUserId(Long userId);

}
