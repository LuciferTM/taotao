package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * <p>Title: ContentCategoryService</p>
 * <p>Description: </p>
 * @author Lucifer
 * @date 2017/8/24
 */
public interface ContentCategoryService {
    List<EUTreeNode> getCategoryList(long parent_id);
    TaotaoResult insertContentCategory(long parentId, String name);
    TaotaoResult deleteContentCategory(long id);
    TaotaoResult updateContentCategory(long id, String name);
}
