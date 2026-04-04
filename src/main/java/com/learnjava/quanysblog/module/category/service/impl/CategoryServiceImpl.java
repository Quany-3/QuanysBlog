package com.learnjava.quanysblog.module.category.service.impl;

import com.learnjava.quanysblog.common.exception.BusinessException;
import com.learnjava.quanysblog.common.result.ResultCode;
import com.learnjava.quanysblog.module.article.entity.Category;
import com.learnjava.quanysblog.module.article.repository.CategoryRepository;
import com.learnjava.quanysblog.module.category.dto.request.CategoryRequest;
import com.learnjava.quanysblog.module.category.dto.response.CategoryResponse;
import com.learnjava.quanysblog.module.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类服务实现类
 * 处理分类相关的业务逻辑
 *
 * @author Quany
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    /**
     * 分类数据访问层（来自 article 模块）
     */
    private final CategoryRepository categoryRepository;

    /**
     * 创建分类
     */
    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        // 检查别名是否已存在
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new BusinessException(ResultCode.DUPLICATE_ERROR, "分类别名已存在");
        }

        // 构建分类实体
        Category category = Category.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .sort(request.getSort() != null ? request.getSort() : 0)
                .build();

        category = categoryRepository.save(category);
        log.info("创建分类成功：{}", category.getName());

        return toCategoryResponse(category);
    }

    /**
     * 更新分类
     */
    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        // 获取分类
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.CATEGORY_NOT_FOUND, "分类不存在"));

        // 检查别名是否与其他分类冲突
        categoryRepository.findBySlug(request.getSlug())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BusinessException(ResultCode.DUPLICATE_ERROR, "分类别名已存在");
                    }
                });

        // 更新字段
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        if (request.getSort() != null) {
            category.setSort(request.getSort());
        }

        category = categoryRepository.save(category);
        log.info("更新分类成功：{}", category.getName());

        return toCategoryResponse(category);
    }

    /**
     * 删除分类
     */
    @Override
    @Transactional
    public void deleteCategory(Long id) {
        // 检查分类是否存在
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException(ResultCode.CATEGORY_NOT_FOUND, "分类不存在");
        }

        categoryRepository.deleteById(id);
        log.info("删除分类成功：{}", id);
    }

    /**
     * 获取分类详情
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.CATEGORY_NOT_FOUND, "分类不存在"));
        return toCategoryResponse(category);
    }

    /**
     * 获取所有分类
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将 Category 实体转换为 CategoryResponse DTO
     */
    private CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .sort(category.getSort())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
