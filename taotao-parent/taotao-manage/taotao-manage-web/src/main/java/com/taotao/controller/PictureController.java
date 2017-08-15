package com.taotao.controller;

import com.taotao.common.util.JsonUtils;
import com.taotao.service.PictuerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by LuciferTM on 2017/8/15.
 */
@Controller
public class PictureController {

    @Autowired
    private PictuerService pictuerService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile) {
        Map result = pictuerService.uploadPicture(uploadFile);
        //为了保证功能的兼容性，需要把Result转换成json格式的字符串。
        String json = JsonUtils.objectToJson(result);
        return json;
    }
}
