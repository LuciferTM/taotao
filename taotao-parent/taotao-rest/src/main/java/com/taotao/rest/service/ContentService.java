package com.taotao.rest.service;

import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/8/25
 */
public interface ContentService {
    List<TbContent> getContentList(long contentCid);
}
