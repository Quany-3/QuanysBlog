package com.learnjava.quanysblog.module.article.controller;

import com.learnjava.quanysblog.common.result.ApiResponse;
import com.learnjava.quanysblog.module.article.dto.request.ArticleRequest;
import com.learnjava.quanysblog.module.article.dto.response.ArticleResponse;
import com.learnjava.quanysblog.module.article.service.ArticleService;
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

/**
 * 文章控制器
 * 处理文章相关的HTTP请求
 *
 * @author Quany
 */
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    /**
     * 文章服务
     */
    private final ArticleService articleService;

    /**
     * 用户数据访问层（用于获取当前用户）
     */
    private final UserRepository userRepository;

    /**
     * 创建文章
     * POST /api/articles
     *
     * @param request 文章创建请求
     * @param authentication 当前登录用户
     * @return 创建的文章响应
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponse>> createArticle(
            @Valid @RequestBody ArticleRequest request,
            Authentication authentication) {
        Long authorId = getCurrentUserId(authentication);
        ArticleResponse response = articleService.createArticle(request, authorId);
        return ResponseEntity.ok(ApiResponse.success("文章创建成功", response));
    }

    /**
     * 获取文章详情
     * GET /api/articles/{id}
     *
     * @param id 文章ID
     * @return 文章响应
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> getArticleById(@PathVariable Long id) {
        ArticleResponse response = articleService.getArticleById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 分页查询文章列表
     * GET /api/articles?page=0&size=10
     *
     * @param page 页码（从0开始）
     * @param size 每页数量
     * @return 文章分页结果
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ArticleResponse>>> getArticlesByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleResponse> articles = articleService.getArticlesByPage(pageable);
        return ResponseEntity.ok(ApiResponse.success(articles));
    }

    /**
     * 更新文章
     * PUT /api/articles/{id}
     *
     * @param id 文章ID
     * @param request 文章更新请求
     * @param authentication 当前登录用户
     * @return 更新后的文章响应
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request,
            Authentication authentication) {
        Long authorId = getCurrentUserId(authentication);
        ArticleResponse response = articleService.updateArticle(id, request, authorId);
        return ResponseEntity.ok(ApiResponse.success("文章更新成功", response));
    }

    /**
     * 删除文章
     * DELETE /api/articles/{id}
     *
     * @param id 文章ID
     * @param authentication 当前登录用户
     * @return 无内容返回
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArticle(
            @PathVariable Long id,
            Authentication authentication) {
        Long authorId = getCurrentUserId(authentication);
        articleService.deleteArticle(id, authorId);
        return ResponseEntity.ok(ApiResponse.success("文章删除成功"));
    }

    /**
     * 按分类查询文章
     * GET /api/articles/category/{categoryId}
     *
     * @param categoryId 分类ID
     * @param page 页码
     * @param size 每页数量
     * @return 文章分页结果
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ArticleResponse>>> getArticlesByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleResponse> articles = articleService.getArticlesByCategory(categoryId, pageable);
        return ResponseEntity.ok(ApiResponse.success(articles));
    }

    /**
     * 按标签查询文章
     * GET /api/articles/tag/{tagId}
     *
     * @param tagId 标签ID
     * @param page 页码
     * @param size 每页数量
     * @return 文章分页结果
     */
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<ApiResponse<Page<ArticleResponse>>> getArticlesByTag(
            @PathVariable Long tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleResponse> articles = articleService.getArticlesByTag(tagId, pageable);
        return ResponseEntity.ok(ApiResponse.success(articles));
    }

    /**
     * 搜索文章
     * GET /api/articles/search?q=keyword
     *
     * @param q 搜索关键词
     * @param page 页码
     * @param size 每页数量
     * @return 文章分页结果
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ArticleResponse>>> searchArticles(
            @RequestParam("q") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleResponse> articles = articleService.searchArticles(q, pageable);
        return ResponseEntity.ok(ApiResponse.success(articles));
    }

    /**
     * 从认证信息中获取当前用户ID
     *
     * @param authentication 认证信息
     * @return 当前用户ID
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
