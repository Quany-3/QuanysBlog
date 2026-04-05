package com.learnjava.quanysblog.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Dotenv 环境变量加载器
 * 在 Spring Boot 启动前将 .env 文件中的变量加载到环境变量中
 * 使 @Value 和 ${VAR:default} 语法可以读取 .env 文件中的配置
 *
 * @author Quany
 */
@Slf4j
public class DotenvInitializer implements EnvironmentPostProcessor {

    private static final String DOTENV_PROPERTY_SOURCE_NAME = "dotenv";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()  // .env 文件不存在时不报错
                    .load();

            Map<String, Object> dotenvMap = new HashMap<>();
            dotenv.entries().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null && value != null) {
                    // 仅当系统属性未设置时才添加，避免覆盖命令行 -D 参数
                    if (System.getProperty(key) == null) {
                        dotenvMap.put(key, value);
                    }
                }
            });

            // 添加到环境变量源，优先级低于系统属性和命令行参数
            environment.getPropertySources()
                    .addFirst(new MapPropertySource(DOTENV_PROPERTY_SOURCE_NAME, dotenvMap));

            log.info("Dotenv 加载成功，共加载 {} 个环境变量", dotenvMap.size());
        } catch (Exception e) {
            log.warn("Dotenv 加载失败，将使用系统环境变量: {}", e.getMessage());
        }
    }
}
