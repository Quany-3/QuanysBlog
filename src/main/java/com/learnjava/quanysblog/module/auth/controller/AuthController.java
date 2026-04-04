package com.learnjava.quanysblog.module.auth.controller;

import com.learnjava.quanysblog.common.result.ApiResponse;
import com.learnjava.quanysblog.module.auth.dto.request.LoginRequest;
import com.learnjava.quanysblog.module.auth.dto.request.RegisterRequest;
import com.learnjava.quanysblog.module.auth.dto.response.AuthResponse;
import com.learnjava.quanysblog.module.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 处理用户注册、登录等认证相关的HTTP请求
 *
 * @author Quany
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * 认证服务
     */
    private final AuthService authService;

    /**
     * 用户注册接口
     * POST /api/auth/register
     *
     * @param request 注册请求（用户名、密码、邮箱）
     * @return 注册成功返回用户信息和JWT令牌
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("注册成功", response));
    }

    /**
     * 用户登录接口
     * POST /api/auth/login
     *
     * @param request 登录请求（用户名、密码）
     * @return 登录成功返回用户信息和JWT令牌
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("登录成功", response));
    }
}
