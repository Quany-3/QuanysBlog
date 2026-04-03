package com.learnjava.quanysblog.config;

import com.learnjava.quanysblog.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全配置类
 * 配置认证、授权、CORS 和密码加密等安全相关功能
 *
 * @author Quany
 */
@Configuration
@EnableWebSecurity          // 启用 Spring Security
@EnableMethodSecurity        // 启用方法级安全注解（如 @PreAuthorize）
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * JWT 认证过滤器，用于验证令牌和设置用户认证信息
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 配置安全过滤器链
     * 定义哪些请求需要认证，哪些可以公开访问
     *
     * @param http HttpSecurity 对象
     * @return 安全过滤器链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF（前后端分离项目中使用 JWT，无需 CSRF 保护）
            .csrf(AbstractHttpConfigurer::disable)
            // 配置 CORS（使用 CorsConfig 中的配置）
            .cors(cors -> cors.configure(http))
            // 配置会话管理为无状态（JWT 令牌认证，不需要会话）
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 配置请求授权规则
            .authorizeHttpRequests(auth -> auth
                // 公开访问：认证相关接口（注册、登录）
                .requestMatchers("/api/auth/**").permitAll()
                // 公开访问：文章查询接口（GET）
                .requestMatchers(HttpMethod.GET, "/api/articles/**").permitAll()
                // 公开访问：分类查询接口（GET）
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                // 公开访问：标签查询接口（GET）
                .requestMatchers(HttpMethod.GET, "/api/tags/**").permitAll()
                // 公开访问：评论查询接口（GET）
                .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                // 公开访问：搜索接口（GET）
                .requestMatchers(HttpMethod.GET, "/api/search").permitAll()
                // 仅管理员访问：后台管理接口
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                // 其他所有请求都需要认证
                .anyRequest().authenticated()
            )
            // 在用户名密码认证过滤器之前添加 JWT 认证过滤器
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 配置认证管理器
     * 用于处理用户名密码认证
     *
     * @param config 认证配置
     * @return 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 配置密码加密器
     * 使用 BCrypt 算法加密密码（单向哈希）
     *
     * @return BCrypt 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
