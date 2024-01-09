package com.bugflix.weblog.security.domain;

import lombok.Getter;
// @Id jpa 용 hibernate id가 아닌 org.springframework.data.annotation.Id import 해야됨
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
// 필드값과 맞춰야하나? https://velog.io/@ililil9482/spring-redis-jpa-redisTemplate-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private final String email;

    private final String refreshToken;

    @TimeToLive
    private final long expiredTime;

    public RefreshToken(String email, String refreshToken, long expiredTime) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.expiredTime = expiredTime;
    }
}
