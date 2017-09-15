package com.taotao.portal.controller;

import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String getItemInfo(@PathVariable Long itemId, Model model){

        ItemInfo itemInfo = itemService.getItemById(itemId);
        model.addAttribute("item", itemInfo);
        return "item";
    }

    @RequestMapping(value = "/item/desc/{itemId}", produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    public String getItemDesc(@PathVariable Long itemId){
        String string = itemService.getItemDescById(itemId);
        return string;
    }

    @RequestMapping(value = "/item/param/{itemId}", produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    public String getItemParam(@PathVariable Long itemId){
        String string = itemService.getItemParamById(itemId);
        return string;
    }

}
