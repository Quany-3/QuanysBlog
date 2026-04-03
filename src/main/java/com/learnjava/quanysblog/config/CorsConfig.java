package com.learnjava.quanysblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CORS（跨域资源共享）配置类
 * 允许前端应用跨域访问后端 API
 *
 * @author Quany
 */
@Configuration
public class CorsConfig {

    /**
     * 配置 CORS 跨域策略
     *
     * @return CORS 配置源
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 允许的前端 origins（开发环境允许本地前端）
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));

        // 允许的 HTTP 方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 允许的请求头
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));

        // 暴露给前端的响应头（前端可以读取这些响应头）
        configuration.setExposedHeaders(List.of("Authorization"));

        // 是否允许携带认证信息（cookies）
        configuration.setAllowCredentials(true);

        // 预检请求（OPTIONS）的缓存时间（秒）
        // 在此期间，浏览器不需要再发送预检请求
        configuration.setMaxAge(3600L);

        // 创建 URL 级别的 CORS 配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有路径应用此 CORS 配置
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
