package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/19
 */
public interface UserService {
    TaotaoResult checkData(String param, Integer type);
}
