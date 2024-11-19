package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.infrastructure.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    private static final long AUTHENTICATION_PERIOD_MINUTE = 30L;

    @Override
    public void addToken(String email, String token) {
        stringRedisTemplate.opsForValue().set(email, token, AUTHENTICATION_PERIOD_MINUTE, TimeUnit.MINUTES);
    }

    @Override
    public String getToken(String email) {

        return stringRedisTemplate.opsForValue().get(email);
    }

    @Override
    public Boolean deleteToken(String email) {
        return stringRedisTemplate.delete(email);
    }
}
