package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/15
 */
public class ItemInfo extends TbItem {

    public String[] getImages() {
        String images = getImage();
        if(images != null){
            return images.split(",");
        }
        else {
            return null;
        }
    }
}
