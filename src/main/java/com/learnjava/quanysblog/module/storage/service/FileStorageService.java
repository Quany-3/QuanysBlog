package com.learnjava.quanysblog.module.storage.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储服务接口
 * 定义文件上传的业务逻辑
 *
 * @author Quany
 */
public interface FileStorageService {

    /**
     * 上传头像图片
     *
     * @param file 上传的文件
     * @param username 上传的用户名（用于生成文件名）
     * @return 头像的访问 URL
     */
    String uploadAvatar(MultipartFile file, String username);
}
