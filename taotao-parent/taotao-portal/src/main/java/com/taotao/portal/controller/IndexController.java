package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LuciferTM on 2017/8/22.
 */
@Controller
public class IndexController {
    //为什么直接请求"/" 就请求到了index？？？？
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
