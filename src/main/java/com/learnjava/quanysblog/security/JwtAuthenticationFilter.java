package com.learnjava.quanysblog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 认证过滤器
 * 继承 OncePerRequestFilter，确保每个请求只执行一次
 * 从请求头中提取 JWT 令牌并验证，设置 Spring Security 认证信息
 *
 * @author Quany
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * JWT 令牌提供者，用于生成和验证令牌
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 用户详情服务，用于加载用户信息
     */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * 过滤器核心方法
     * 处理每个 HTTP 请求的认证逻辑
     *
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param filterChain 过滤器链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取 JWT 令牌
        String token = getJwtFromRequest(request);

        // 如果令牌存在且有效，则进行认证
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // 从令牌中解析用户名
            String username = jwtTokenProvider.getUsernameFromToken(token);
            // 加载用户详情（包含权限信息）
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 创建认证令牌（ credentials 设为 null，表示不需要验证凭证）
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            // 设置认证详情（包含请求信息）
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 将认证信息设置到 Spring Security 上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 继续执行过滤器链，让后续过滤器处理
        filterChain.doFilter(request, response);
    }

    /**
     * 从 HTTP 请求头中提取 JWT 令牌
     * 令牌格式：Bearer {token}
     *
     * @param request HTTP 请求
     * @return JWT 令牌字符串，如果不存在则返回 null
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        // 获取 Authorization 请求头
        String bearerToken = request.getHeader("Authorization");
        // 检查是否以 "Bearer " 开头
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // 去掉 "Bearer " 前缀，返回实际的令牌
            return bearerToken.substring(7);
        }
        return null;
    }
}
