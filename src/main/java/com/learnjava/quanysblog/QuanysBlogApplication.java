package com.learnjava.quanysblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * QuanysBlog 博客系统启动类
 *
 * @author Quany
 */
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class QuanysBlogApplication {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        SpringApplication.run(QuanysBlogApplication.class, args);
    }
}
