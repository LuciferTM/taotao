package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/1
 */
public interface RedisService {
    TaotaoResult syncContent(long contentCid);
}
