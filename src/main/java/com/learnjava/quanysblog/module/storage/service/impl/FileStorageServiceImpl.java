package com.learnjava.quanysblog.module.storage.service.impl;

import com.learnjava.quanysblog.common.exception.BusinessException;
import com.learnjava.quanysblog.common.result.ResultCode;
import com.learnjava.quanysblog.config.MinioConfig;
import com.learnjava.quanysblog.module.storage.service.FileStorageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

/**
 * 文件存储服务实现类
 * 使用 MinIO S3 兼容 API 存储文件
 *
 * @author Quany
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    /**
     * 存储桶下的头像目录前缀
     */
    private static final String AVATAR_DIRECTORY = "avatars/";

    @Override
    public String uploadAvatar(MultipartFile file, String username) {
        // 1. 校验文件是否为空
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "头像文件不能为空");
        }

        // 2. 校验文件大小
        if (file.getSize() > minioConfig.getAvatarMaxSize()) {
            throw new BusinessException(ResultCode.BAD_REQUEST,
                    "头像文件大小不能超过 " + (minioConfig.getAvatarMaxSize() / 1024 / 1024) + "MB");
        }

        // 3. 校验文件类型
        String contentType = file.getContentType();
        boolean allowed = Arrays.stream(minioConfig.getAvatarAllowedTypes().split(","))
                .map(String::trim)
                .anyMatch(type -> type.equalsIgnoreCase(contentType));
        if (!allowed) {
            throw new BusinessException(ResultCode.BAD_REQUEST,
                    "头像文件类型不支持，仅支持：JPEG、PNG、GIF、WebP");
        }

        // 4. 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String objectName = AVATAR_DIRECTORY + username + "/" + UUID.randomUUID().toString() + extension;

        // 5. 上传到 MinIO
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucket())
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            log.error("头像上传失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "头像上传失败，请稍后重试");
        }

        // 6. 构建访问 URL
        String publicUrl = minioConfig.getPublicUrl();
        String avatarUrl = publicUrl.endsWith("/") ? publicUrl + objectName : publicUrl + "/" + objectName;
        log.info("用户 {} 头像上传成功：{}", username, avatarUrl);

        return avatarUrl;
    }
}
