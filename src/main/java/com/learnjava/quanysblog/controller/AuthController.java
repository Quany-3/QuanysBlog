package com.learnjava.quanysblog.controller;

import com.learnjava.quanysblog.dto.request.LoginRequest;
import com.learnjava.quanysblog.dto.request.RegisterRequest;
import com.learnjava.quanysblog.dto.response.ApiResponse;
import com.learnjava.quanysblog.dto.response.AuthResponse;
import com.learnjava.quanysblog.entity.User;
import com.learnjava.quanysblog.repository.UserRepository;
import com.learnjava.quanysblog.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
     * 认证管理器，用于验证用户凭据
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 用户数据访问层
     */
    private final UserRepository userRepository;

    /**
     * 密码加密器，用于加密用户密码
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * JWT 令牌提供者，用于生成和验证令牌
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 用户注册接口
     * POST /api/auth/register
     *
     * @param request 注册请求（用户名、密码、邮箱）
     * @return 注册成功返回用户信息和JWT令牌
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("用户名已存在"));
        }

        // 检查邮箱是否已被注册
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("邮箱已被注册"));
        }

        // 创建新用户
        User user = User.builder()
                .username(request.getUsername())
                // 使用 BCrypt 加密密码
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                // 默认普通用户角色
                .role(User.Role.USER)
                // 默认激活状态
                .status(User.Status.ACTIVE)
                .build();

        // 保存用户到数据库
        userRepository.save(user);

        // 生成 JWT 令牌
        String token = jwtTokenProvider.generateToken(user.getUsername());

        // 构建响应数据
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

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
        // 使用 Spring Security 进行认证
        // 如果用户名或密码错误，会抛出 BadCredentialsException
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 认证成功，生成 JWT 令牌
        String token = jwtTokenProvider.generateToken(authentication);

        // 从数据库获取用户信息
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        // 构建响应数据
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

        return ResponseEntity.ok(ApiResponse.success("登录成功", response));
    }
}
