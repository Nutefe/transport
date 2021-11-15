package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.RefreshTokenDao;
import com.bluerizon.transport.entity.RefreshTokens;
import com.bluerizon.transport.exception.TokenRefreshException;
import com.bluerizon.transport.repository.RefreshTokenRepository;
import com.bluerizon.transport.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService implements RefreshTokenDao {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsersRepository userRepository;

    @Value("${app.refreshExpirationDateInMs}")
    private Long refreshTokenDurationMs;

    @Override
    public Optional<RefreshTokens> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokens createRefreshToken(Long userId) {
        RefreshTokens refreshTokens = new RefreshTokens();

        refreshTokens.setUsers(userRepository.findById(userId).get());
        refreshTokens.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshTokens.setToken(UUID.randomUUID().toString());

        refreshTokens = refreshTokenRepository.save(refreshTokens);
        return refreshTokens;
    }

    @Override
    public RefreshTokens verifyExpiration(RefreshTokens token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Override
    public int deleteByUserId(Long userId) {
        return 0;
    }
}
