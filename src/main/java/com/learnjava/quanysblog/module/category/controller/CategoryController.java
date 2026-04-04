package com.learnjava.quanysblog.module.category.controller;

import com.learnjava.quanysblog.common.result.ApiResponse;
import com.learnjava.quanysblog.module.category.dto.request.CategoryRequest;
import com.learnjava.quanysblog.module.category.dto.response.CategoryResponse;
import com.learnjava.quanysblog.module.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 * 处理分类相关的HTTP请求
 *
 * @author Quany
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    /**
     * 分类服务
     */
    private final CategoryService categoryService;

    /**
     * 创建分类
     * POST /api/categories
     *
     * @param request 分类创建请求
     * @return 创建的分类响应
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.success("分类创建成功", response));
    }

    /**
     * 获取所有分类
     * GET /api/categories
     *
     * @return 分类列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    /**
     * 获取分类详情
     * GET /api/categories/{id}
     *
     * @param id 分类ID
     * @return 分类响应
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 更新分类
     * PUT /api/categories/{id}
     *
     * @param id 分类ID
     * @param request 分类更新请求
     * @return 更新后的分类响应
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(ApiResponse.success("分类更新成功", response));
    }

    /**
     * 删除分类
     * DELETE /api/categories/{id}
     *
     * @param id 分类ID
     * @return 无内容返回
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("分类删除成功"));
    }
}
