package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title:验证用户数据的接口</p>
 * <p>Description: </p>
 *
 * 这里要注意的就是 springmvc支持jsonp使用MappingJacksonValue
 * example:
 *
 *  @RequestMapping("directives.json")
    @ResponseBody
    public MappingJacksonValue directives(String callback) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue("you can put everything here.");
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
 }

    有callback支持jsonp的时候返回不一定是TaotaoResult，所以接口的返回值设置为Object
 *
 * @author Lucifer
 * @date 2017/9/19
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback){
        TaotaoResult result = null;
        if(StringUtils.isBlank(param)){
            result = TaotaoResult.build(400, "内容不能为空");
        }
        if(type == null){
            result = TaotaoResult.build(400, "type不能为空");
        }
        else if(type < 1 || type > 3){
            result = TaotaoResult.build(400, "Invalid Input");
        }
        if(result != null){
            if(callback != null){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return result;
            }

        }
        try {
            result = userService.checkData(param, type);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        if(callback != null){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }

        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object regist(TbUser user){
        try {
            TaotaoResult result = userService.createUser(user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object userLogin(String username, String password,
                            HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return TaotaoResult.build(400, "账号名或密码不能为空");
        }
        try {
            TaotaoResult result = userService.login(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TaotaoResult result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
            return result;
        }
    }
    @RequestMapping(value="/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback){
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        if(StringUtils.isBlank(callback)){
            return result;
        } else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }
}
