package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuciferTM on 2017/8/23.
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    private List<?> getCatList(Long parentId){
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List resultList = new ArrayList<>();
        int count = 0;
        for(TbItemCat itemCat:list){
            //判断是否为叶子节点
            if(itemCat.getIsParent()){
                CatNode catNode = new CatNode();
                if(parentId == 0) {
                    catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
                } else{
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/" + itemCat.getId() + ".html");
                catNode.setItem(getCatList(itemCat.getId()));
                resultList.add(catNode);
                count ++;
                //第一层只取14条记录
                if (parentId == 0 && count >=14) {
                    break;
                }
            //叶子节点
            } else {
                resultList.add("/products/"+itemCat.getId()+".html|" + itemCat.getName());
            }
        }
        return resultList;
    }

    @Override
    public CatResult getCatResult() {
        CatResult catResult = new CatResult();
        catResult.setData(getCatList(0L));
        return catResult;
    }
}
