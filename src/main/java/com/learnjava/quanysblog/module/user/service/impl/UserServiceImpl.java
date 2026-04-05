package com.learnjava.quanysblog.module.user.service.impl;

import com.learnjava.quanysblog.common.exception.BusinessException;
import com.learnjava.quanysblog.common.result.ResultCode;
import com.learnjava.quanysblog.module.auth.repository.UserRepository;
import com.learnjava.quanysblog.module.user.dto.UpdateProfileRequest;
import com.learnjava.quanysblog.module.user.entity.User;
import com.learnjava.quanysblog.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 *
 * @author Quany
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND, "用户不存在"));
    }

    @Override
    @Transactional
    public User updateProfile(String username, UpdateProfileRequest request) {
        User user = getCurrentUser(username);

        // 更新邮箱
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            // 检查邮箱是否已被其他用户使用
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BusinessException(ResultCode.EMAIL_EXISTS, "邮箱已被其他用户使用");
            }
            user.setEmail(request.getEmail());
        }

        // 更新个人简介
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }

        // 更新头像
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }

        User savedUser = userRepository.save(user);
        log.info("用户 {} 资料更新成功", username);
        return savedUser;
    }
}
