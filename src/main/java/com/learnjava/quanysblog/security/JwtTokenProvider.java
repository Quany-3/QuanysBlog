package com.learnjava.quanysblog.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 令牌提供者
 * 负责 JWT 令牌的生成、验证和解析
 *
 * @author Quany
 */
@Component
public class JwtTokenProvider {

    /**
     * 签名密钥，用于 HMAC-SHA 算法签名
     */
    private final SecretKey key;

    /**
     * 令牌过期时间（毫秒）
     */
    private final long jwtExpiration;

    /**
     * 构造方法，注入 JWT 配置
     *
     * @param jwtSecret JWT 密钥
     * @param jwtExpiration 过期时间
     */
    public JwtTokenProvider(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration}") long jwtExpiration) {
        // 使用 HMAC-SHA 算法生成签名密钥
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * 根据认证信息生成 JWT 令牌
     *
     * @param authentication Spring Security 认证对象
     * @return JWT 令牌字符串
     */
    public String generateToken(Authentication authentication) {
        // 从认证对象中获取用户详情
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(userDetails.getUsername());
    }

    /**
     * 根据用户名生成 JWT 令牌
     * 令牌包含用户名、签发时间、过期时间等信息
     *
     * @param username 用户名
     * @return JWT 令牌字符串
     */
    public String generateToken(String username) {
        Date now = new Date();
        // 计算过期时间：当前时间 + 配置的过期时间
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(username)                    // 设置令牌主题（用户名）
                .issuedAt(now)                        // 设置签发时间
                .expiration(expiryDate)              // 设置过期时间
                .signWith(key)                       // 使用密钥签名
                .compact();                          // 生成紧凑的字符串形式
    }

    /**
     * 从 JWT 令牌中解析用户名
     *
     * @param token JWT 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        // 解析令牌并获取声明（payload）
        Claims claims = Jwts.parser()
                .verifyWith(key)                      // 验证签名
                .build()
                .parseSignedClaims(token)             // 解析签名
                .getPayload();                        // 获取载荷
        return claims.getSubject();                   // 返回用户名
    }

    /**
     * 验证 JWT 令牌是否有效
     * 检查令牌签名是否正确、是否过期
     *
     * @param token JWT 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            // 尝试解析令牌，如果签名错误或格式不对会抛出异常
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 令牌无效：签名错误、已过期、格式错误等
            return false;
        }
    }
}
