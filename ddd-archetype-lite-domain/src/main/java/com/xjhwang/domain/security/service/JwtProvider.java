package com.xjhwang.domain.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.exception.ApplicationException;
import com.xjhwang.types.util.IdUtils;
import com.xjhwang.types.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            .id(IdUtils.fastSimpleUUID())
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
    
    public void verify(String token) {
        
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(signKey.getEncoded())).build();
        try {
            jwtVerifier.verify(token);
        } catch (SignatureVerificationException e) {
            log.error("Token - 签名无效", e);
            throw new ApplicationException(ResponseCode.TOKEN_SIGNATURE_INVALID);
        } catch (TokenExpiredException e) {
            log.error("Token - 过期", e);
            throw new ApplicationException(ResponseCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.error("Token - 无效", e);
            throw new ApplicationException(ResponseCode.TOKEN_INVALID);
        }
    }
    
}
