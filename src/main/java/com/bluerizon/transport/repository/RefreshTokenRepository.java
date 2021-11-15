package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokens, Long> {
    @Override
    Optional<RefreshTokens> findById(Long id);

    Optional<RefreshTokens> findByToken(String token);
}
