package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;

import java.util.List;

/**
 * Created by lucifer on 8/8/2017.
 */
public interface ItemCatService {
    List<EUTreeNode> getCatList(long parentId);
}
