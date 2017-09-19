package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.common.jedis.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

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

        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            if(!StringUtils.isBlank(json)){
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return TaotaoResult.ok(tbItemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok(tbItemDesc);
    }

    @Override
    public TaotaoResult getItemParam(long itemId) {
        //添加缓存
        try {
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            //判断是否有值
            if (!StringUtils.isBlank(json)) {
                //把json转换成java对象
                TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(paramItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据商品id查询规格参数
        //设置查询条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size()>0) {
            TbItemParamItem paramItem = list.get(0);
            try {
                //把商品信息写入缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
                //设置key的有效期
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return TaotaoResult.ok(paramItem);
        }
        return TaotaoResult.build(400, "无此商品规格");
    }
}
