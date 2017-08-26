package com.taotao.portal.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value="/httpclient/post", method= RequestMethod.POST,
            produces= MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String testPost(String username, String password) {
        String result = "username:" + username + "\tpassword:" + password;
        System.out.println(result);
        return "username:" + username + ",password:" + password;
    }
}
