package com.learnjava.quanysblog.module.user.controller;

import com.learnjava.quanysblog.common.result.ApiResponse;
import com.learnjava.quanysblog.module.storage.service.FileStorageService;
import com.learnjava.quanysblog.module.user.dto.UpdateProfileRequest;
import com.learnjava.quanysblog.module.user.entity.User;
import com.learnjava.quanysblog.module.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户控制器
 * 处理用户资料相关的 HTTP 请求
 *
 * @author Quany
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    /**
     * 获取当前用户资料
     * GET /api/user/profile
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<User>> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails.getUsername());
        user.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 更新当前用户资料
     * PUT /api/user/profile
     */
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<User>> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request) {
        User user = userService.updateProfile(userDetails.getUsername(), request);
        user.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success("资料更新成功", user));
    }

    /**
     * 上传头像
     * POST /api/user/avatar
     */
    @PostMapping("/avatar")
    public ResponseEntity<ApiResponse<String>> uploadAvatar(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) {
        String avatarUrl = fileStorageService.uploadAvatar(file, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("头像上传成功", avatarUrl));
    }
}
