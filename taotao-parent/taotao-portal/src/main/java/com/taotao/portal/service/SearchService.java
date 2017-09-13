package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/13
 */

public interface SearchService {
    SearchResult search(String query, int page);
}
