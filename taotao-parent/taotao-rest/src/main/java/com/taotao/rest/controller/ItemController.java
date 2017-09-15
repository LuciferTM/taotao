package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/15
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("info/{itemId}")
    public TaotaoResult getItemBaseInfo(@PathVariable Long itemId){
        return itemService.getItemBaseInfo(itemId);
    }

}
