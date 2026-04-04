package com.learnjava.quanysblog.module.comment.controller;

import com.learnjava.quanysblog.common.result.ApiResponse;
import com.learnjava.quanysblog.module.comment.dto.request.CommentRequest;
import com.learnjava.quanysblog.module.comment.dto.response.CommentResponse;
import com.learnjava.quanysblog.module.comment.service.CommentService;
import com.learnjava.quanysblog.module.user.entity.User;
import com.learnjava.quanysblog.module.auth.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 * 处理评论相关的HTTP请求
 *
 * @author Quany
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    /**
     * 评论服务
     */
    private final CommentService commentService;

    /**
     * 用户数据访问层（用于获取当前用户）
     */
    private final UserRepository userRepository;

    /**
     * 创建评论
     * POST /api/comments
     *
     * @param request 评论创建请求
     * @param authentication 当前登录用户
     * @return 创建的评论响应
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @Valid @RequestBody CommentRequest request,
            Authentication authentication) {
        Long authorId = getCurrentUserId(authentication);
        CommentResponse response = commentService.createComment(request, authorId);
        return ResponseEntity.ok(ApiResponse.success("评论创建成功", response));
    }

    /**
     * 获取文章的评论列表
     * GET /api/comments/article/{articleId}
     *
     * @param articleId 文章ID
     * @return 评论列表
     */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentsByArticle(
            @PathVariable Long articleId) {
        List<CommentResponse> comments = commentService.getCommentsByArticle(articleId);
        return ResponseEntity.ok(ApiResponse.success(comments));
    }

    /**
     * 删除评论
     * DELETE /api/comments/{id}
     *
     * @param id 评论ID
     * @param authentication 当前登录用户
     * @return 无内容返回
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long id,
            Authentication authentication) {
        Long authorId = getCurrentUserId(authentication);
        commentService.deleteComment(id, authorId);
        return ResponseEntity.ok(ApiResponse.success("评论删除成功"));
    }

    /**
     * 获取所有评论（管理员）
     * GET /api/comments/admin/all
     *
     * @param page 页码
     * @param size 每页数量
     * @return 评论分页结果
     */
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> comments = commentService.getAllComments(pageable);
        return ResponseEntity.ok(ApiResponse.success(comments));
    }

    /**
     * 管理员删除评论
     * DELETE /api/comments/admin/{id}
     *
     * @param id 评论ID
     * @return 无内容返回
     */
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCommentByAdmin(@PathVariable Long id) {
        commentService.deleteCommentByAdmin(id);
        return ResponseEntity.ok(ApiResponse.success("评论删除成功"));
    }

    /**
     * 从认证信息中获取当前用户ID
     */
    private Long getCurrentUserId(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new com.learnjava.quanysblog.common.exception.BusinessException(
                    com.learnjava.quanysblog.common.result.ResultCode.UNAUTHORIZED, "用户未登录");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new com.learnjava.quanysblog.common.exception.BusinessException(
                        com.learnjava.quanysblog.common.result.ResultCode.USER_NOT_FOUND, "用户不存在"));
        return user.getId();
    }
}
