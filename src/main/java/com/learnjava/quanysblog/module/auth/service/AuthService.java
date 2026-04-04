package com.learnjava.quanysblog.module.auth.service;

import com.learnjava.quanysblog.module.auth.dto.request.LoginRequest;
import com.learnjava.quanysblog.module.auth.dto.request.RegisterRequest;
import com.learnjava.quanysblog.module.auth.dto.response.AuthResponse;

/**
 * 认证服务接口
 * 定义用户注册和登录的业务逻辑
 *
 * @author Quany
 */
public interface AuthService {

    /**
     * 用户注册
     * 创建新用户并返回JWT令牌
     *
     * @param request 注册请求（用户名、密码、邮箱）
     * @return 注册成功返回认证响应（JWT令牌和用户信息）
     */
    AuthResponse register(RegisterRequest request);

    /**
     * 用户登录
     * 验证用户凭据并返回JWT令牌
     *
     * @param request 登录请求（用户名、密码）
     * @return 登录成功返回认证响应（JWT令牌和用户信息）
     */
    AuthResponse login(LoginRequest request);
}
