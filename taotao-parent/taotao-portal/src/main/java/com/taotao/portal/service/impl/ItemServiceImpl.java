package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/15
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${ITME_INFO_URL}")
    private String ITME_INFO_URL;


    @Override
    public ItemInfo getItemById(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL+ITME_INFO_URL+itemId);
            if(!StringUtils.isBlank(json)){
                TaotaoResult result = TaotaoResult.formatToPojo(json, ItemInfo.class);
                if(result.getStatus() == 200){
                    return (ItemInfo)result.getData();
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
