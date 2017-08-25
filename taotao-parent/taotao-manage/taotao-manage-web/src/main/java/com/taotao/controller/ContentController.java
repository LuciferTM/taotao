package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/8/25
 */

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("query/list")
    @ResponseBody
    public EUDataGridResult getContentList(int page, int rows, long categoryId){
        return contentService.getContentList(page, rows, categoryId);
    }
}
