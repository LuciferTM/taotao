package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/11
 */
public interface SearchService {
    SearchResult search(String query, int page, int rows) throws Exception;
}
