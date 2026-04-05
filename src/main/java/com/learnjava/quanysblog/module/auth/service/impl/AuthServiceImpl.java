package com.learnjava.quanysblog.module.auth.service.impl;

import com.learnjava.quanysblog.common.exception.BusinessException;
import com.learnjava.quanysblog.common.result.ResultCode;
import com.learnjava.quanysblog.module.auth.dto.request.LoginRequest;
import com.learnjava.quanysblog.module.auth.dto.request.RegisterRequest;
import com.learnjava.quanysblog.module.auth.dto.response.AuthResponse;
import com.learnjava.quanysblog.module.auth.repository.UserRepository;
import com.learnjava.quanysblog.module.auth.service.AuthService;
import com.learnjava.quanysblog.module.user.entity.User;
import com.learnjava.quanysblog.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 认证服务实现类
 * 处理用户注册和登录的业务逻辑
 *
 * @author Quany
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

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
     * 用户注册
     * 创建新用户并返回JWT令牌
     *
     * @param request 注册请求（用户名、密码、邮箱）
     * @return 注册成功返回认证响应（JWT令牌和用户信息）
     */
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("注册失败：用户名已存在 - {}", request.getUsername());
            throw new BusinessException(ResultCode.USERNAME_EXISTS, "用户名已存在");
        }

        // 检查邮箱是否已被注册
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("注册失败：邮箱已被注册 - {}", request.getEmail());
            throw new BusinessException(ResultCode.EMAIL_EXISTS, "邮箱已被注册");
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
        log.info("用户注册成功：{}", user.getUsername());

        // 生成 JWT 令牌
        String token = jwtTokenProvider.generateToken(user.getUsername());

        // 构建响应数据
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .avatar(user.getAvatar())
                .build();
    }

    /**
     * 用户登录
     * 验证用户凭据并返回JWT令牌
     *
     * @param request 登录请求（用户名、密码）
     * @return 登录成功返回认证响应（JWT令牌和用户信息）
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        // 使用 Spring Security 进行认证
        // 如果用户名或密码错误，会抛出 BadCredentialsException
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 认证成功，生成 JWT 令牌
        String token = jwtTokenProvider.generateToken(authentication);

        // 从数据库获取用户信息
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND, "用户不存在"));

        log.info("用户登录成功：{}", user.getUsername());

        // 构建响应数据
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .avatar(user.getAvatar())
                .build();
    }
}
