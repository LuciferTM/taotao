package com.taotao.controller;

/**
 * Created by LuciferTM on 2017/8/15.
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamContorller {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId){
        TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
        return result;
    }

    @RequestMapping("/save/{itemCatId}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long itemCatId, String paramData){
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(itemCatId);
        itemParam.setParamData(paramData);
        TaotaoResult result = itemParamService.insertItemParam(itemParam);
        return result;
    }
}
