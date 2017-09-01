package com.taotao.rest.service.impl;

import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/8/25
 */
@Service
public class ContentServiceImpl implements ContentService {

   @Value("${INDEX_CONTENT_REDIS_KEY}")
   private String INDEX_CONTENT_REDIS_KEY;

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public List<TbContent> getContentList(long contentCid) {

        //取缓存
        try {
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid+"");
            if(!StringUtils.isBlank(result)){
                List<TbContent> list = JsonUtils.jsonToList(result, TbContent.class);
                return list;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);

        //设置缓存
        try{
            String jsonList = JsonUtils.objectToJson(list);
            Long result = jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid+"", jsonList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
