package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/8/26
 */

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;

    @Override
    public String getContentList() {
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        //将字符串转换成TaotaoResult
        try{
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            //获取内容列表
            List<TbContent> list = (List<TbContent>)taotaoResult.getData();
            List<Map> resultList = new ArrayList<>();
            for (TbContent tbContent:list){
                Map map = new HashMap<>();
                map.put("src", tbContent.getPic());
                map.put("height", 240);
                map.put("width", 670);
                map.put("srcB", tbContent.getPic2());
                map.put("widthB", 550);
                map.put("heightB", 240);
                map.put("href", tbContent.getUrl());
                map.put("alt", tbContent.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
