package com.taotao.rest.controller;

import com.taotao.common.util.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**o
 * Created by LuciferTM on 2017/8/23.
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

//    //produces 设置返回是个json 以及字符编码格式
//    @RequestMapping(value="/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//    @ResponseBody
//    public String getItemCatList(String callback){
//        CatResult catResult = itemCatService.getCatResult();
//        //把pojo转换成为json串
//        String json = JsonUtils.objectToJson(catResult);
//        return callback + "(" + json + ");";
//    }

    //另外一种方法，需要springmvc 4.1以上
    @RequestMapping(value="/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    //Object会自动转换成json
    public Object getItemCatList(String callback){
        CatResult catResult = itemCatService.getCatResult();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
}
