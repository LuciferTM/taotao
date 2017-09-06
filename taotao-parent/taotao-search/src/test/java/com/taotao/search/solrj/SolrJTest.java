package com.taotao.search.solrj;

import com.taotao.search.BaseTaotaoSearchTestCase;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/5
 */

public class SolrJTest extends BaseTaotaoSearchTestCase{

    @Autowired
    private SolrServer solrServer;

    @Test
    public void insertDocument() throws Exception{

        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id", "test001");
        solrInputDocument.addField("item_title", "测试商品123");
        solrInputDocument.addField("item_price", 12345);
        //将文档对象写入索引库
        solrServer.add(solrInputDocument);
        //提交
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws Exception {
        solrServer.deleteById("test001");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
}
