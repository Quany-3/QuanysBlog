package com.learnjava.quanysblog.config;

import com.learnjava.quanysblog.module.auth.repository.UserRepository;
import com.learnjava.quanysblog.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员账户初始化服务
 * 在应用启动时检查并创建初始管理员账户（如果不存在）
 * 管理员凭据通过环境变量配置：
 * - ADMIN_USERNAME: 管理员用户名
 * - ADMIN_PASSWORD: 管理员密码
 * - ADMIN_EMAIL: 管理员邮箱
 *
 * @author Quany
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializerService implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME:}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD:}")
    private String adminPassword;

    @Value("${ADMIN_EMAIL:}")
    private String adminEmail;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // 如果环境变量未配置，则跳过初始化
        if (adminUsername.isBlank() || adminPassword.isBlank() || adminEmail.isBlank()) {
            log.warn("管理员环境变量未配置，跳过管理员初始化。需设置: ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_EMAIL");
            return;
        }

        // 检查管理员是否已存在
        if (userRepository.existsByRole(User.Role.ADMIN)) {
            log.info("管理员账户已存在:");
            return;
        }

        // 创建管理员账户
        User admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .email(adminEmail)
                .role(User.Role.ADMIN)
                .status(User.Status.ACTIVE)
                .build();

        userRepository.save(admin);
        log.info("管理员账户创建成功: {} ({})", adminUsername, adminEmail);
    }
}
