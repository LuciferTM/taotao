package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;

import java.util.List;

/**
 * <p>Title: ContentCategoryService</p>
 * <p>Description: </p>
 * @author Lucifer
 * @date 2017/8/24
 */
public interface ContentCategoryService {
    List<EUTreeNode> getCategoryList(long parent_id);
}
