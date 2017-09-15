package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/15
 */
public interface ItemService {
    TaotaoResult getItemBaseInfo(long itemId);
    TaotaoResult getItemDesc(long itemId);
    TaotaoResult getItemParam(long itemId);
}
