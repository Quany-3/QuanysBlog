package com.learnjava.quanysblog.module.tag.service;

import com.learnjava.quanysblog.module.tag.dto.request.TagRequest;
import com.learnjava.quanysblog.module.tag.dto.response.TagResponse;

import java.util.List;

/**
 * 标签服务接口
 * 定义标签相关的业务逻辑
 *
 * @author Quany
 */
public interface TagService {

    /**
     * 创建标签
     *
     * @param request 标签创建请求
     * @return 创建的标签响应
     */
    TagResponse createTag(TagRequest request);

    /**
     * 更新标签
     *
     * @param id 标签ID
     * @param request 标签更新请求
     * @return 更新后的标签响应
     */
    TagResponse updateTag(Long id, TagRequest request);

    /**
     * 删除标签
     *
     * @param id 标签ID
     */
    void deleteTag(Long id);

    /**
     * 获取标签详情
     *
     * @param id 标签ID
     * @return 标签响应
     */
    TagResponse getTagById(Long id);

    /**
     * 获取所有标签
     *
     * @return 标签列表
     */
    List<TagResponse> getAllTags();
}
