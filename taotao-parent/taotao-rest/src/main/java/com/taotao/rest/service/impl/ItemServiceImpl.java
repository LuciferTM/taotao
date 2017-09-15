package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        //查询缓存
        //redis之中的hash是不能设置缓存时间的所有使用string，并加以":"分隔的命名规范
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            if(!StringUtils.isBlank(json)){
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return TaotaoResult.ok(tbItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        try {
            //设置缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(item);
    }

    @Override
    public TaotaoResult getItemDesc(long itemId) {
        return null;
    }

    @Override
    public TaotaoResult getItemParam(long itemId) {
        return null;
    }
}
