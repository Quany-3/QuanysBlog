package com.learnjava.quanysblog.module.tag.controller;

import com.learnjava.quanysblog.common.result.ApiResponse;
import com.learnjava.quanysblog.module.tag.dto.request.TagRequest;
import com.learnjava.quanysblog.module.tag.dto.response.TagResponse;
import com.learnjava.quanysblog.module.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器
 * 处理标签相关的HTTP请求
 *
 * @author Quany
 */
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    /**
     * 标签服务
     */
    private final TagService tagService;

    /**
     * 创建标签
     * POST /api/tags
     *
     * @param request 标签创建请求
     * @return 创建的标签响应
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TagResponse>> createTag(
            @Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.createTag(request);
        return ResponseEntity.ok(ApiResponse.success("标签创建成功", response));
    }

    /**
     * 获取所有标签
     * GET /api/tags
     *
     * @return 标签列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {
        List<TagResponse> tags = tagService.getAllTags();
        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    /**
     * 获取标签详情
     * GET /api/tags/{id}
     *
     * @param id 标签ID
     * @return 标签响应
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> getTagById(@PathVariable Long id) {
        TagResponse response = tagService.getTagById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 更新标签
     * PUT /api/tags/{id}
     *
     * @param id 标签ID
     * @param request 标签更新请求
     * @return 更新后的标签响应
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.updateTag(id, request);
        return ResponseEntity.ok(ApiResponse.success("标签更新成功", response));
    }

    /**
     * 删除标签
     * DELETE /api/tags/{id}
     *
     * @param id 标签ID
     * @return 无内容返回
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok(ApiResponse.success("标签删除成功"));
    }
}
