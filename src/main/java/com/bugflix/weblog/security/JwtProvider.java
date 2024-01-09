package com.bugflix.weblog.security;


import com.bugflix.weblog.security.domain.RefreshToken;
import com.bugflix.weblog.security.repository.RefreshTokenRepository;
import com.bugflix.weblog.security.service.CustomUserDetailServiceImpl;
import com.bugflix.weblog.user.domain.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    private final CustomUserDetailServiceImpl customUserDetailService;

    @Value("${springboot.jwt.secret.key}")
    private String secret;

    private Key secretKey;

    @Value("${springboot.jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${springboot.jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String createAccessToken(String account, List<Authority> roles) {
        Claims claims = Jwts.claims()
                .subject(account)
                .add("roles", roles)
                .build();

        Date now = new Date();

        String token = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessExpirationTime))
                .signWith(secretKey)
                .compact();

        return token;
    }

    public String createRefreshToken(String account, List<Authority> roles) {
        Claims claims = Jwts.claims()
                .subject(account)
                .add("roles", roles)
                .build();

        Date now = new Date();

        String refreshToken = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshExpirationTime))
                .signWith(secretKey)
                .compact();

        // redis에 저장, redisTemplate과 CrudRepository의 저장방식이 다른가보다.
        // 조회는 Repository를 통해 수행하므로 저장도 repository로 저장
        refreshTokenRepository.save(new RefreshToken(account, refreshToken, refreshExpirationTime));
        return refreshToken;
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getAccount(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("Bearer ")) {
                throw new IllegalStateException("Token 정보가 존재하지 않음");
            }
            token = token.split(" ")[1].trim();
        } catch (Exception e) {
            return null;
        }
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
