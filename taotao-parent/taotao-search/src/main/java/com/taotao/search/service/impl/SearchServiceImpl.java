package com.taotao.search.service.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/11
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String query, int page, int rows) throws Exception{
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(query);
        //设置分页
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        //设置默认搜素域
        solrQuery.set("df", "item_keywords");
        //设置高亮显示
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        SearchResult searchResult = searchDao.search(solrQuery);
        long recordCount = searchResult.getRecordCount();
        long pageCount = recordCount / rows;
        if (recordCount % rows > 0) {
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage(page);
        return searchResult;
    }
}
