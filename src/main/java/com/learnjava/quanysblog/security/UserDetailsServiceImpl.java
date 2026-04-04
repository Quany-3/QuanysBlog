package com.learnjava.quanysblog.security;

import com.learnjava.quanysblog.module.user.entity.User;
import com.learnjava.quanysblog.module.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 用户详情服务实现
 * 实现 Spring Security 的 UserDetailsService 接口
 * 用于加载用户认证信息
 *
 * @author Quany
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户数据访问层
     */
    private final UserRepository userRepository;

    /**
     * 根据用户名加载用户详情
     * Spring Security 在认证时会调用此方法
     *
     * @param username 用户名
     * @return 用户详情对象（Spring Security 标准格式）
     * @throws UsernameNotFoundException 如果用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库查找用户，如果不存在抛出异常
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 将用户实体转换为 Spring Security 的 UserDetails 对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),           // 用户名
                user.getPassword(),           // 加密后的密码
                // 将用户角色转换为 Spring Security 权限
                // 格式：ROLE_ADMIN 或 ROLE_USER
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
