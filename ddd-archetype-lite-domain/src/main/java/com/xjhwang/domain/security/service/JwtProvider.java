package com.xjhwang.domain.security.service;

import com.xjhwang.types.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt处理
 *
 * @author 黄雪杰 on 2024-07-11 16:05
 */
@Component
public class JwtProvider {
    
    @Getter
    private final String issuer;
    
    private final SecretKey signKey;
    
    public JwtProvider(@Value("${application.jwt.sign-key}") String signKey, @Value("${application.jwt.issuer}") String issuer) {
        
        this.issuer = issuer;
        this.signKey = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));
    }
    
    public String encode(String subject, long ttlMillis, Map<String, Object> claims) {
        
        if (claims == null) {
            claims = new HashMap<>();
        }
        // 签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 签发
        JwtBuilder jwtBuilder = Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .subject(subject)
            .issuer(issuer)
            .signWith(signKey, Jwts.SIG.HS256);
        // 过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.expiration(exp);
        }
        return jwtBuilder.compact();
    }
    
    public Claims decode(String token) {
        
        return Jwts.parser()
            .verifyWith(signKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    
    public void verify(String token, String phone) {
        
        Claims claims = decode(token);
        Date expiration = claims.getExpiration();
        if (expiration != null && expiration.before(new Date())) {
            throw new IllegalStateException("无效令牌");
        }
        if (!StringUtils.equals(issuer, claims.getIssuer())) {
            throw new IllegalStateException("无效令牌");
        }
        if (!StringUtils.equals(phone, claims.getSubject())) {
            throw new IllegalStateException("无效令牌");
        }
    }
    
}
