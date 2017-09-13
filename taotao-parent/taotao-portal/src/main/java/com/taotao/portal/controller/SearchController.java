package com.taotao.portal.controller;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/13
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String query,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model){

        SearchResult searchResult = searchService.search(query, page);
        //向jsp页面传递参数
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        return "search";
    }
}
