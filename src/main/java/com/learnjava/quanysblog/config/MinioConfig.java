package com.learnjava.quanysblog.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO 配置类
 * 配置 S3 兼容存储客户端
 *
 * @author Quany
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    /**
     * MinIO 服务地址
     */
    private String endpoint;

    /**
     * Access Key（用户名）
     */
    private String accessKey;

    /**
     * Secret Key（密码）
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucket;

    /**
     * 公共访问 URL（用于生成头像 URL）
     */
    private String publicUrl;

    /**
     * 头像最大文件大小（字节）
     */
    private long avatarMaxSize;

    /**
     * 允许的头像 MIME 类型
     */
    private String avatarAllowedTypes;

    /**
     * 创建 MinIO 客户端
     *
     * @return MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
