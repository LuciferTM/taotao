package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * Created by LuciferTM on 2017/8/15.
 */
public interface ItemParamService {
    TaotaoResult getItemParamByCid(long cid);
    TaotaoResult insertItemParam(TbItemParam itemParam);
}
