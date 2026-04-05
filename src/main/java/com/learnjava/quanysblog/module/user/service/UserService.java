package com.learnjava.quanysblog.module.user.service;

import com.learnjava.quanysblog.module.user.dto.UpdateProfileRequest;
import com.learnjava.quanysblog.module.user.entity.User;

/**
 * 用户服务接口
 * 定义用户相关的业务逻辑
 *
 * @author Quany
 */
public interface UserService {

    /**
     * 获取当前登录用户信息
     *
     * @param username 用户名
     * @return 用户实体
     */
    User getCurrentUser(String username);

    /**
     * 更新用户资料
     *
     * @param username 用户名
     * @param request 更新请求
     * @return 更新后的用户实体
     */
    User updateProfile(String username, UpdateProfileRequest request);
}
