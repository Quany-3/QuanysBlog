package com.learnjava.quanysblog.module.tag.service.impl;

import com.learnjava.quanysblog.common.exception.BusinessException;
import com.learnjava.quanysblog.common.result.ResultCode;
import com.learnjava.quanysblog.module.article.entity.Tag;
import com.learnjava.quanysblog.module.article.repository.TagRepository;
import com.learnjava.quanysblog.module.tag.dto.request.TagRequest;
import com.learnjava.quanysblog.module.tag.dto.response.TagResponse;
import com.learnjava.quanysblog.module.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签服务实现类
 * 处理标签相关的业务逻辑
 *
 * @author Quany
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    /**
     * 标签数据访问层（来自 article 模块）
     */
    private final TagRepository tagRepository;

    /**
     * 创建标签
     */
    @Override
    @Transactional
    public TagResponse createTag(TagRequest request) {
        // 检查别名是否已存在
        if (tagRepository.existsBySlug(request.getSlug())) {
            throw new BusinessException(ResultCode.DUPLICATE_ERROR, "标签别名已存在");
        }

        // 检查名称是否已存在
        if (tagRepository.existsByName(request.getName())) {
            throw new BusinessException(ResultCode.DUPLICATE_ERROR, "标签名称已存在");
        }

        // 构建标签实体
        Tag tag = Tag.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .color(request.getColor() != null ? request.getColor() : "#1890ff")
                .build();

        tag = tagRepository.save(tag);
        log.info("创建标签成功：{}", tag.getName());

        return toTagResponse(tag);
    }

    /**
     * 更新标签
     */
    @Override
    @Transactional
    public TagResponse updateTag(Long id, TagRequest request) {
        // 获取标签
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.TAG_NOT_FOUND, "标签不存在"));

        // 检查别名是否与其他标签冲突
        tagRepository.findBySlug(request.getSlug())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BusinessException(ResultCode.DUPLICATE_ERROR, "标签别名已存在");
                    }
                });

        // 检查名称是否与其他标签冲突
        tagRepository.findByName(request.getName())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BusinessException(ResultCode.DUPLICATE_ERROR, "标签名称已存在");
                    }
                });

        // 更新字段
        tag.setName(request.getName());
        tag.setSlug(request.getSlug());
        if (request.getColor() != null) {
            tag.setColor(request.getColor());
        }

        tag = tagRepository.save(tag);
        log.info("更新标签成功：{}", tag.getName());

        return toTagResponse(tag);
    }

    /**
     * 删除标签
     */
    @Override
    @Transactional
    public void deleteTag(Long id) {
        // 检查标签是否存在
        if (!tagRepository.existsById(id)) {
            throw new BusinessException(ResultCode.TAG_NOT_FOUND, "标签不存在");
        }

        tagRepository.deleteById(id);
        log.info("删除标签成功：{}", id);
    }

    /**
     * 获取标签详情
     */
    @Override
    @Transactional(readOnly = true)
    public TagResponse getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.TAG_NOT_FOUND, "标签不存在"));
        return toTagResponse(tag);
    }

    /**
     * 获取所有标签
     */
    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::toTagResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将 Tag 实体转换为 TagResponse DTO
     */
    private TagResponse toTagResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .color(tag.getColor())
                .createdAt(tag.getCreatedAt())
                .build();
    }
}
