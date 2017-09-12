package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/12
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping(value="/query", method= RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q") String query,
                               @RequestParam(defaultValue="1") Integer page,
                               @RequestParam(defaultValue="60") Integer rows){
        try{
            SearchResult searchResult = searchService.search(query,page,rows);
            return TaotaoResult.ok(searchResult);
        }catch (Exception e){
            System.out.println(ExceptionUtil.getStackTrace(e));
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }
}
