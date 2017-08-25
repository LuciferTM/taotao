package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/8/25
 */
public interface ContentService {
    EUDataGridResult getContentList(int page, int rows, long categoryId);
}
