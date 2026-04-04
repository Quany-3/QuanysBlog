package com.learnjava.quanysblog.module.category.service;

import com.learnjava.quanysblog.module.category.dto.request.CategoryRequest;
import com.learnjava.quanysblog.module.category.dto.response.CategoryResponse;

import java.util.List;

/**
 * 分类服务接口
 * 定义分类相关的业务逻辑
 *
 * @author Quany
 */
public interface CategoryService {

    /**
     * 创建分类
     *
     * @param request 分类创建请求
     * @return 创建的分类响应
     */
    CategoryResponse createCategory(CategoryRequest request);

    /**
     * 更新分类
     *
     * @param id 分类ID
     * @param request 分类更新请求
     * @return 更新后的分类响应
     */
    CategoryResponse updateCategory(Long id, CategoryRequest request);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void deleteCategory(Long id);

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类响应
     */
    CategoryResponse getCategoryById(Long id);

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    List<CategoryResponse> getAllCategories();
}
